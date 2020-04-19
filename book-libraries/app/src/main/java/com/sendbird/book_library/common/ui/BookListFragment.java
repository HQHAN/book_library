package com.sendbird.book_library.common.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.sendbird.book_library.R;
import com.sendbird.book_library.databinding.FragmentNewBinding;

public class BookListFragment extends Fragment {
    protected FragmentNewBinding viewBinding;
    protected BookListAdapter bookListAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewBinding = FragmentNewBinding.inflate(inflater);


        return viewBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bookListAdapter = new BookListAdapter(this::navigateToDetail);
        viewBinding.newBookList.setAdapter(bookListAdapter);
        viewBinding.newBookList.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void toggleLoading(boolean shouldShowLoading) {
        if(shouldShowLoading) {
            viewBinding.progress.setVisibility(View.VISIBLE);
            viewBinding.newBookList.setVisibility(View.GONE);
        } else {
            viewBinding.progress.setVisibility(View.GONE);
            viewBinding.newBookList.setVisibility(View.VISIBLE);
        }
    }

    protected void navigateToDetail(Long isbn) { }
}
