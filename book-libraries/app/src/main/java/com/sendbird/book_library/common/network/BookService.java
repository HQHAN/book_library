package com.sendbird.book_library.common.network;

import com.sendbird.book_library.ui.home.models.BookList;

import io.reactivex.Observable;
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
    Observable<ResponseBody> getBookDetail(@Path("isbn") String isbn);

    @GET("search/{query}")
    Observable<ResponseBody> searchBook(@Path("query") String query);

    @GET("search/{query}/{page}")
    Observable<ResponseBody> searchBookWithPage(@Path("query") String query, @Path("page") String page);
}
