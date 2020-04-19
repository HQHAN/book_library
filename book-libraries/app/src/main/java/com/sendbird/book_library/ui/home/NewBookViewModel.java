package com.sendbird.book_library.ui.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;

import com.sendbird.book_library.common.network.BaseViewModel;
import com.sendbird.book_library.common.network.NetworkManager;
import com.sendbird.book_library.model.home.BookList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NewBookViewModel extends BaseViewModel {
    public MutableLiveData<BookList> newBookList = new MutableLiveData<>();

    public void fetchNewBookList() {
        compositeDisposable.add(
                NetworkManager.getInstance().bookServiceApi.getNewBookList()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe( item -> {
                        isLoading.setValue(true);
                    })
                    .doOnSuccess(item -> {
                        newBookList.setValue(item);
                        isLoading.setValue(false);
                    })
                    .doOnError(error -> {
                        isLoading.setValue(false);
                    })
                    .subscribe()
        );
    }
}