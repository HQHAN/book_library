package com.sendbird.book_library.search.ui;

import androidx.lifecycle.MutableLiveData;

import com.sendbird.book_library.common.network.BaseViewModel;
import com.sendbird.book_library.common.network.NetworkManager;
import com.sendbird.book_library.home.model.BookList;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SearchViewModel extends BaseViewModel {
    public MutableLiveData<BookList> searchList = new MutableLiveData<>();

    public void searchBookWithQuery(String query) {
        compositeDisposable.add(
                NetworkManager.getInstance().bookServiceApi.searchBook(query)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe( item -> {
                            isLoading.setValue(true);
                        })
                        .doOnSuccess(item -> {
                            searchList.setValue(item);
                            isLoading.setValue(false);
                        })
                        .doOnError(error -> {
                            isLoading.setValue(false);
                        })
                        .subscribe()
        );
    }
}