package com.sendbird.book_library.common.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sendbird.book_library.R;
import com.sendbird.book_library.databinding.BookRecyclerViewItemBinding;
import com.sendbird.book_library.home.model.BookList.Book;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.ViewHolder>  {
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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BookRecyclerViewItemBinding itemBinding = BookRecyclerViewItemBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new ViewHolder(itemBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(position < 0 || position >= dataSet.size()) return;
        holder.bind(dataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView book_thumb_view;
        private TextView title;
        private TextView subTitle;
        private TextView isbn;
        private TextView price;
        private TextView url;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            book_thumb_view = itemView.findViewById(R.id.book_thumb);
            title = itemView.findViewById(R.id.title);
            subTitle = itemView.findViewById(R.id.subtitle);
            isbn = itemView.findViewById(R.id.isbn);
            price = itemView.findViewById(R.id.price);
            url = itemView.findViewById(R.id.book_url);

            itemView.setOnClickListener(v -> {
                navigateListener.navigateToDetail(dataSet.get(getAdapterPosition()).isbn13);
            });
        }

        public void bind(Book book) {
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

        private void bindAllTexts(Book book) {
            title.setText(book.title);
            subTitle.setText(book.subTitle);
            isbn.setText(String.valueOf(book.isbn13));
            price.setText(book.price);
            url.setText(book.url);
        }
    }
}
