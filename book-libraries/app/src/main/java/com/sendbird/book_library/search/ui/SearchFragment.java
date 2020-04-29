package com.sendbird.book_library.search.ui;

import android.app.SearchManager;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.cursoradapter.widget.CursorAdapter;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sendbird.book_library.R;
import com.sendbird.book_library.common.ui.BookListFragment;

public class SearchFragment extends BookListFragment {

    private SearchViewModel searchViewModel;
    private SimpleCursorAdapter cursorAdapter;
    private SearchResultAdapter searchResultAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);
        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        setHasOptionsMenu(true);
        observeViewModel();
        initListeners();

        searchResultAdapter = new SearchResultAdapter();
        swapAdapter(searchResultAdapter);

        return root;
    }

    private void initListeners() {
        viewBinding.newBookList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int visibleItemCount = layoutManager.getChildCount();
                int lastVisibleItemPos = layoutManager.findLastVisibleItemPosition();

                searchViewModel.listScrolled(visibleItemCount, lastVisibleItemPos);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.action_search_menu, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setIconifiedByDefault(false);
        AutoCompleteTextView searchAutoCompleteTextView = searchView.findViewById(R.id.search_src_text);
        searchAutoCompleteTextView.setThreshold(1);

        String[] from = {SearchManager.SUGGEST_COLUMN_TEXT_1};
        int[] to = {R.id.item_label};
        cursorAdapter = new SimpleCursorAdapter(
                requireContext(),
                R.layout.search_suggestion_item_layout,
                null,
                from,
                to,
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        searchView.setSuggestionsAdapter(cursorAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchViewModel.searchBookInitially(query);
                saveSearchQuery(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Cursor cursor = getRecentSuggestions(newText);
                cursorAdapter.swapCursor(cursor);
                return false;
            }
        });

        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                return false;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                searchView.setQuery(getSuggestionText(position), true);
                return true;
            }
        });
    }

    private void observeViewModel() {
        searchViewModel.searchList.observe(getViewLifecycleOwner(), s -> {
            searchResultAdapter.submitList(s);
        });

        searchViewModel.isRequestInProgress.observe(getViewLifecycleOwner(), isRequestActive -> {
            viewBinding.progress.setVisibility(isRequestActive ? View.VISIBLE : View.GONE);
        });

        searchViewModel.isLoading.observe(getViewLifecycleOwner(), this::toggleLoading);
    }

    private void saveSearchQuery(String query) {
        SearchRecentSuggestions suggestions = new SearchRecentSuggestions(requireContext(),
                SearchHistoryProvider.AUTHORITY, SearchHistoryProvider.MODE);
        suggestions.saveRecentQuery(query, null);
    }

    private Cursor getRecentSuggestions(String query) {
        Uri.Builder uriBuilder = new Uri.Builder()
                .scheme(ContentResolver.SCHEME_CONTENT)
                .authority(SearchHistoryProvider.AUTHORITY);
        uriBuilder.appendPath(SearchManager.SUGGEST_URI_PATH_QUERY);

        String selection = " ?";
        String[] selArgs = new String[] { query };
        Uri uri = uriBuilder.build();
        return requireActivity().getContentResolver().query(uri, null, selection, selArgs, null);
    }

    private String getSuggestionText(int position) {
        Cursor cursor = cursorAdapter.getCursor();
        if (cursor != null && !cursor.isClosed() && position >= 0 && position < cursor.getCount()) {
            cursor.moveToPosition(position);
            return cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1));
        }
        return null;
    }
}
