package com.sendbird.book_library.common.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sendbird.book_library.R;
import com.sendbird.book_library.home.model.BookList;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.function.Consumer;

public class BookListViewHolder extends RecyclerView.ViewHolder {
    private ImageView book_thumb_view;
    private TextView title;
    private TextView subTitle;
    private TextView isbn;
    private TextView price;
    private TextView url;

    public BookListViewHolder(@NonNull View itemView, Consumer<Integer> onClickConsumer) {
        super(itemView);
        book_thumb_view = itemView.findViewById(R.id.book_thumb);
        title = itemView.findViewById(R.id.title);
        subTitle = itemView.findViewById(R.id.subtitle);
        isbn = itemView.findViewById(R.id.isbn);
        price = itemView.findViewById(R.id.price);
        url = itemView.findViewById(R.id.book_url);

        itemView.setOnClickListener(v -> {
            onClickConsumer.accept(getAdapterPosition());
        });
    }

    public void bind(BookList.Book book) {
        Picasso.get().load(book.image).resize(640, 640).into(book_thumb_view, new Callback() {
            @Override
            public void onSuccess() {
                bindAllTexts(book);
            }

            @Override
            public void onError(Exception e) {
                bindAllTexts(book);
            }
        });
    }

    private void bindAllTexts(BookList.Book book) {
        title.setText(book.title);
        subTitle.setText(book.subTitle);
        isbn.setText(String.valueOf(book.isbn13));
        price.setText(book.price);
        url.setText(book.url);
    }
}
