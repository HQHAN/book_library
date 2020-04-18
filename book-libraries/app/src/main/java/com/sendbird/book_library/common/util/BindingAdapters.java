package com.sendbird.book_library.common.util;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Picasso;

public class BindingAdapters {
    private static final BindingAdapters ourInstance = new BindingAdapters();

    public static BindingAdapters getInstance() {
        return ourInstance;
    }

    private BindingAdapters() {
    }

    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView view, String url) {
        Picasso.get().load(url).into(view);
    }

}
