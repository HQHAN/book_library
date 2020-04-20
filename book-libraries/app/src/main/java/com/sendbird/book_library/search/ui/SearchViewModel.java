package com.sendbird.book_library.search.ui;

import androidx.lifecycle.MutableLiveData;

import com.sendbird.book_library.common.network.BaseViewModel;
import com.sendbird.book_library.common.network.NetworkManager;
import com.sendbird.book_library.home.model.BookList.Book;


import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SearchViewModel extends BaseViewModel {
    public MutableLiveData<List<Book>> searchList = new MutableLiveData<>();
    public MutableLiveData<Boolean> isRequestInProgress = new MutableLiveData<>(false);
    private static final int PAGE_SIZE = 10;
    private static final int LOAD_MORE_THRESHOLD = 4;

    private int lastRequestedPage = 1;
    private String lastQuery = "";

    public void searchBookInitially(String query) {
        lastRequestedPage = 1;
        lastQuery = query;
        requestAndSaveData(query);

        List<Book> cachedResult = SearchResultMemoryCache.getInstance().getCachedResult(query);
        if(cachedResult != null) {
            searchList.setValue(cachedResult);
        }
    }

    private void requestMore(String query) {
        requestAndSaveData(query);
    }

    public void listScrolled(int visibleItemCount, int lastVisibleItemPosition, int totalItemCount) {
        if (visibleItemCount + lastVisibleItemPosition + LOAD_MORE_THRESHOLD >= totalItemCount) {
            requestMore(lastQuery);
        }
    }

    private void requestAndSaveData(String query) {
        if (isRequestInProgress.getValue() || query.isEmpty()) return;
        isRequestInProgress.setValue(true);

        compositeDisposable.add(
                NetworkManager.getInstance().bookServiceApi.searchBookWithPage(query, lastRequestedPage)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSuccess(item -> {
                            int start = Math.max(0, (lastRequestedPage - 1) * 10);
                            int end = lastRequestedPage * PAGE_SIZE - 1;
                            // replace cached one with the new one in place
                            List<Book> searchResult = SearchResultMemoryCache.getInstance()
                                    .upsertSearchResult(
                                        query,
                                        item.books,
                                        start,
                                        end
                                    );
                            searchList.setValue(searchResult);

                            lastRequestedPage++;
                            isRequestInProgress.setValue(false);
                        })
                        .doOnError(error -> {
                            isRequestInProgress.setValue(false);
                        })
                        .subscribe()
        );
    }
}