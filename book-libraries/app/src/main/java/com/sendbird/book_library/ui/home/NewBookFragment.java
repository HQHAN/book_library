package com.sendbird.book_library.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.sendbird.book_library.databinding.FragmentNewBinding;


public class NewBookFragment extends Fragment {

    private NewBookViewModel newBookViewModel;
    private FragmentNewBinding viewBinding;
    private NewBookListAdapter newBookListAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        newBookViewModel = ViewModelProviders.of(this).get(NewBookViewModel.class);
        viewBinding = FragmentNewBinding.inflate(inflater);

        newBookListAdapter = new NewBookListAdapter();
        viewBinding.newBookList.setAdapter(newBookListAdapter);
        viewBinding.newBookList.setLayoutManager(new LinearLayoutManager(getContext()));

        observeViewModel();
        return viewBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newBookViewModel.fetchNewBookList();
    }

    private void observeViewModel() {
        newBookViewModel.newBookList.observe(getViewLifecycleOwner(), s -> {
            newBookListAdapter.setData(s.books);
        });

        newBookViewModel.isLoading.observe(getViewLifecycleOwner(), isLoading -> {
            if(isLoading) {
                viewBinding.progress.setVisibility(View.VISIBLE);
                viewBinding.newBookList.setVisibility(View.GONE);
            } else {
                viewBinding.progress.setVisibility(View.GONE);
                viewBinding.newBookList.setVisibility(View.VISIBLE);
            }
        });
    }
}
