package com.sendbird.book_library.bookmark.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;

import com.sendbird.book_library.R;
import com.sendbird.book_library.common.SharedViewModel;
import com.sendbird.book_library.common.ui.BookListFragment;
import com.sendbird.book_library.common.ui.SwipeToDeleteCallback;
import com.sendbird.book_library.home.model.BookList.Book;

import java.util.List;

public class BookmarkFragment extends BookListFragment {
    private SharedViewModel sharedViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = super.onCreateView(inflater,container,savedInstanceState);
        sharedViewModel = ViewModelProviders.of(requireActivity()).get(SharedViewModel.class);
        // add a swipe to delete feature
        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new SwipeToDeleteCallback(bookListAdapter, idToDelete -> sharedViewModel.removeBookMarkItem(idToDelete)));
        itemTouchHelper.attachToRecyclerView(viewBinding.newBookList);
        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.book_list_sort_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.sort_price:
                List<Book> sorted = sharedViewModel.getBookMarkList((o1, o2) ->
                        Float.compare(Float.parseFloat(o2.price.replace("$", "")), Float.parseFloat(o1.price.replace("$", ""))));
                bookListAdapter.setData(sorted);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bookListAdapter.setData(sharedViewModel.getBookMarkList());
        toggleLoading(false);
    }

    @Override
    protected void navigateToDetail(Long isbn) {
        NavDirections action = BookmarkFragmentDirections.actionNavigationBookmarkToBookDetailFragment(isbn);
        Navigation.findNavController(getView()).navigate(action);
    }
}
