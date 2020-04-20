package com.sendbird.book_library.common.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sendbird.book_library.databinding.BookRecyclerViewItemBinding;
import com.sendbird.book_library.home.model.BookList.Book;

import java.util.ArrayList;
import java.util.List;

public class BookListAdapter extends RecyclerView.Adapter<BookListViewHolder>  {
    public interface NavigateListener {
        void navigateToDetail(Long isbn);
    }
    public BookListAdapter(NavigateListener listener) {
        navigateListener = listener;
    }

    private List<Book> dataSet = new ArrayList<>();
    private NavigateListener navigateListener;

    public void setData(List<Book> books) {
        dataSet.clear();
        dataSet.addAll(books);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BookRecyclerViewItemBinding itemBinding = BookRecyclerViewItemBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new BookListViewHolder(itemBinding.getRoot(), position -> navigateListener.navigateToDetail(dataSet.get(position).isbn13));
    }

    @Override
    public void onBindViewHolder(@NonNull BookListViewHolder holder, int position) {
        if(position < 0 || position >= dataSet.size()) return;
        holder.bind(dataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
