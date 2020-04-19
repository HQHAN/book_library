package com.sendbird.book_library.detail.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.sendbird.book_library.R;
import com.sendbird.book_library.databinding.FragmentBookDetailBinding;

public class BookDetailFragment extends Fragment {
    private FragmentBookDetailBinding dataBinding;
    private BookDetailViewModel detailViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        detailViewModel = ViewModelProviders.of(this).get(BookDetailViewModel.class);
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_book_detail, container, false);
        dataBinding.setLifecycleOwner(this);
        dataBinding.setViewmodel(detailViewModel);

        observeLiveData();

        return dataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        long isbn = BookDetailFragmentArgs.fromBundle(getArguments()).getIsbn();
        detailViewModel.fetchBookDetail(isbn);
    }

    private void observeLiveData() {
        detailViewModel.isLoading.observe(getViewLifecycleOwner(), isLoading -> {
            if(isLoading) {
                dataBinding.progressBar.setVisibility(View.VISIBLE);
                dataBinding.bookDetailContainer.setVisibility(View.GONE);
            } else {
                dataBinding.progressBar.setVisibility(View.GONE);
                dataBinding.bookDetailContainer.setVisibility(View.VISIBLE);
            }
        });
    }
}
