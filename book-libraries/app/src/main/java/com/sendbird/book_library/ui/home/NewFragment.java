package com.sendbird.book_library.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.sendbird.book_library.R;
import com.sendbird.book_library.databinding.FragmentNewBinding;


public class NewFragment extends Fragment {

    private NewViewModel newViewModel;
    private FragmentNewBinding viewBinding;
    private NewBookListAdapter newBookListAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        newViewModel = ViewModelProviders.of(this).get(NewViewModel.class);
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
        newViewModel.fetchNewBookList();
    }

    private void observeViewModel() {
        newViewModel.newBookList.observe(getViewLifecycleOwner(), s -> {
            newBookListAdapter.setData(s.books);
        });
    }
}
