package com.sendbird.book_library.search.ui;

import android.util.Log;

import com.sendbird.book_library.home.model.BookList.Book;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchResultMemoryCache {
    private static final SearchResultMemoryCache ourInstance = new SearchResultMemoryCache();

    public static SearchResultMemoryCache getInstance() {
        return ourInstance;
    }

    private Map<String, List<Book>> cache = new HashMap<>();

    private SearchResultMemoryCache() {
    }

    public List<Book> getCachedResult(String query) {
        return cache.get(query);
    }

    public List<Book> upsertSearchResult(String query, List<Book> bookList, int start, int end) {
        List<Book> cachedBookList = getCachedResult(query);
        if(cachedBookList == null) {
            cache.put(query, bookList);
            cachedBookList = bookList;
        } else {
            if(start >= cachedBookList.size()) {
                cachedBookList.addAll(bookList);
            } else {
                end = Math.min(cachedBookList.size() , end + 1);
                List<Book> window = cachedBookList.subList(start, end);
                window.clear();
                window.addAll(bookList);
                Log.d("HANS", "page replace with server data ! Start : " + start + " End : " + end);
            }
        }
        return cachedBookList;
    }
}
