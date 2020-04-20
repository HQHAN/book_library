package com.sendbird.book_library.common.ui;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.sendbird.book_library.home.model.BookList.Book;

import java.util.function.Consumer;

public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {
    private BookListAdapter mAdapter;
    private Consumer<Long> deleteResultListener;

    public SwipeToDeleteCallback(BookListAdapter adapter, Consumer<Long> deleteResult) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        mAdapter = adapter;
        deleteResultListener = deleteResult;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        Book deleted = mAdapter.deleteItem(position);
        deleteResultListener.accept(deleted != null ? deleted.isbn13 : -1);
    }
}
