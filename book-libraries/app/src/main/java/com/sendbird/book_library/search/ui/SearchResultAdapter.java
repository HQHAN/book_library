package com.sendbird.book_library.search.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.sendbird.book_library.common.ui.BookListViewHolder;
import com.sendbird.book_library.databinding.BookRecyclerViewItemBinding;
import com.sendbird.book_library.home.model.BookList.Book;

public class SearchResultAdapter extends ListAdapter<Book, BookListViewHolder> {

    public SearchResultAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public BookListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BookRecyclerViewItemBinding itemBinding = BookRecyclerViewItemBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new BookListViewHolder(itemBinding.getRoot(), position -> {});
    }

    @Override
        public void onBindViewHolder(BookListViewHolder holder, int position) {
            Book book = getItem(position);
            if (book != null) {
                holder.bind(book);
            }
        }
        public static final DiffUtil.ItemCallback<Book> DIFF_CALLBACK =
                new DiffUtil.ItemCallback<Book>() {
                    @Override
                    public boolean areItemsTheSame(
                            @NonNull Book oldBook, @NonNull Book newBook) {
                        // User properties may have changed if reloaded from the DB, but ID is fixed
                        return oldBook.isbn13.equals(newBook.isbn13);
                    }
                    @Override
                    public boolean areContentsTheSame(
                            @NonNull Book oldBook, @NonNull Book newBook) {
                        // NOTE: if you use equals, your object must properly override Object#equals()
                        // Incorrectly returning false here will result in too many animations.
                        return oldBook.isSameContents(newBook);
                    }
                };
}
