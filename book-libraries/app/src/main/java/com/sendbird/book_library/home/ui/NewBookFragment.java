package com.sendbird.book_library.home.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.sendbird.book_library.common.SharedViewModel;
import com.sendbird.book_library.common.ui.BookListFragment;

public class NewBookFragment extends BookListFragment {

    private NewBookViewModel newBookViewModel;
    private SharedViewModel sharedViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);
        newBookViewModel = ViewModelProviders.of(this).get(NewBookViewModel.class);
        sharedViewModel = ViewModelProviders.of(requireActivity()).get(SharedViewModel.class);

        observeViewModel();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newBookViewModel.fetchNewBookList();
    }

    private void observeViewModel() {
        newBookViewModel.newBookList.observe(getViewLifecycleOwner(), s -> {
            bookListAdapter.setData(s.books);
            sharedViewModel.setNewBookList(s.books);
        });

        newBookViewModel.isLoading.observe(getViewLifecycleOwner(), this::toggleLoading);
    }

    @Override
    protected void navigateToDetail(Long isbn) {
        NavDirections action = NewBookFragmentDirections.actionNavigationNewToBookDetailFragment(isbn);
        Navigation.findNavController(getView()).navigate(action);
    }
}
