package com.sendbird.book_library.detail.models;

import com.google.gson.annotations.SerializedName;

public class BookDetail {
    @SerializedName("error")
    public String error;
    @SerializedName("title")
    public String title;
    @SerializedName("subtitle")
    public String subTitle;
    @SerializedName("authors")
    public String authors;
    @SerializedName("publisher")
    public String publisher;
    @SerializedName("language")
    public String language;
    @SerializedName("isbn10")
    public Long isbn10;
    @SerializedName("isbn13")
    public Long isbn13;
    @SerializedName("pages")
    public String pages;
    @SerializedName("year")
    public String year;
    @SerializedName("rating")
    public String rating;
    @SerializedName("desc")
    public String desc;
    @SerializedName("price")
    public String price;
    @SerializedName("image")
    public String image;
    @SerializedName("url")
    public String url;
}
