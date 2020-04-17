package com.sendbird.book_library.ui.home.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookList {
    @SerializedName("error")
    public int error;
    @SerializedName("total")
    public int total;
    @SerializedName("books")
    public List<Book> books;

    public class Book {
        @SerializedName("title")
        public String title;
        @SerializedName("subTitle")
        public String subTitle;
        @SerializedName("isbn13")
        public Long isbn13;
        @SerializedName("price")
        public String price;
        @SerializedName("image")
        public String image;
        @SerializedName("url")
        public String url;
    }
}


