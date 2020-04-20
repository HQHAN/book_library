package com.sendbird.book_library.common.network;

import com.sendbird.book_library.detail.models.BookDetail;
import com.sendbird.book_library.home.model.BookList;

import io.reactivex.Single;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Retrofit services
 */
public interface BookService {
    @GET("new")
    Single<BookList> getNewBookList();

    @GET("books/{isbn}")
    Single<BookDetail> getBookDetail(@Path("isbn") long isbn);

    @GET("search/{query}")
    Single<BookList> searchBook(@Path("query") String query);

    @GET("search/{query}/{page}")
    Single<ResponseBody> searchBookWithPage(@Path("query") String query, @Path("page") String page);
}
