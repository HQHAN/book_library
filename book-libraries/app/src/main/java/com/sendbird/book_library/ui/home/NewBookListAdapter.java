package com.sendbird.book_library.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.sendbird.book_library.R;
import com.sendbird.book_library.databinding.BookRecyclerViewItemBinding;
import com.sendbird.book_library.model.home.BookList.Book;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class NewBookListAdapter extends RecyclerView.Adapter<NewBookListAdapter.ViewHolder>  {
    private List<Book> dataSet = new ArrayList<>();

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
                NavDirections action = NewFragmentDirections.actionNavigationNewToBookDetailFragment(
                        dataSet.get(getAdapterPosition()).isbn13
                );
                Navigation.findNavController(v).navigate(action);
            });
        }

        public void bind(Book book) {
            title.setText(book.title);
            subTitle.setText(book.subTitle);
            isbn.setText(String.valueOf(book.isbn13));
            price.setText(book.price);
            url.setText(book.url);

            Picasso.get().load(book.image).resize(640, 640).into(book_thumb_view);
        }
    }
}
