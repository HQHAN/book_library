package com.sendbird.book_library.home.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookList {
    @SerializedName("error")
    public int error;
    @SerializedName("total")
    public int total;
    @SerializedName("books")
    public List<Book> books;
    @SerializedName("page")
    public int page;

    public class Book {
        @SerializedName("title")
        public String title;
        @SerializedName("subtitle")
        public String subTitle;
        @SerializedName("isbn13")
        public Long isbn13;
        @SerializedName("price")
        public String price;
        @SerializedName("image")
        public String image;
        @SerializedName("url")
        public String url;

        public boolean isSameContents(Book another) {
            return title.equals(another.title) &&
                    subTitle.equals(another.subTitle) &&
                    isbn13.equals(another.isbn13) &&
                    price.equals(another.price) &&
                    image.equals(another.image) &&
                    url.equals(another.url);
        }
    }
}


