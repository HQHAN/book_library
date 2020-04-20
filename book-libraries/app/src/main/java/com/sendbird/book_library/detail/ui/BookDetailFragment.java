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
import com.sendbird.book_library.common.SharedViewModel;
import com.sendbird.book_library.databinding.FragmentBookDetailBinding;

public class BookDetailFragment extends Fragment {
    private FragmentBookDetailBinding dataBinding;
    private BookDetailViewModel detailViewModel;
    private SharedViewModel sharedViewModel;
    private Long currentIsbn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        detailViewModel = ViewModelProviders.of(this).get(BookDetailViewModel.class);
        sharedViewModel = ViewModelProviders.of(requireActivity()).get(SharedViewModel.class);

        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_book_detail, container, false);
        dataBinding.setLifecycleOwner(this);
        dataBinding.setViewmodel(detailViewModel);

        observeLiveData();
        setListeners();

        return dataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        currentIsbn = BookDetailFragmentArgs.fromBundle(getArguments()).getIsbn();
        detailViewModel.fetchBookDetail(currentIsbn);
        sharedViewModel.markVisitedBookDetail(currentIsbn);
        initUi();
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

    private void setListeners() {
        dataBinding.bookMarkButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) {
                sharedViewModel.addBookMarkItem(currentIsbn);
            } else {
                sharedViewModel.removeBookMarkItem(currentIsbn);
            }
        });
    }

    private void initUi() {
        dataBinding.bookMarkButton.setChecked(
                sharedViewModel.isBookMarked(currentIsbn)
        );
    }
}
