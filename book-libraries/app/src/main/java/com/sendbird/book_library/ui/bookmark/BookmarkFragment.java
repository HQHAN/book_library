package com.sendbird.book_library.ui.bookmark;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.sendbird.book_library.SharedViewModel;
import com.sendbird.book_library.common.ui.BookListFragment;

public class BookmarkFragment extends BookListFragment {
    private SharedViewModel sharedViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = super.onCreateView(inflater,container,savedInstanceState);
        sharedViewModel = ViewModelProviders.of(requireActivity()).get(SharedViewModel.class);
        return root;
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
