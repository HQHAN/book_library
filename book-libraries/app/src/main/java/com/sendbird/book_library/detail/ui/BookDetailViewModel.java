package com.sendbird.book_library.detail.ui;

import androidx.lifecycle.MutableLiveData;

import com.sendbird.book_library.common.network.BaseViewModel;
import com.sendbird.book_library.common.network.NetworkManager;
import com.sendbird.book_library.detail.models.BookDetail;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BookDetailViewModel extends BaseViewModel {
    public MutableLiveData<BookDetail> detailMutableLiveData = new MutableLiveData<>();

    public void fetchBookDetail(long isbn) {
        compositeDisposable.add(
                NetworkManager.getInstance().bookServiceApi.getBookDetail(isbn)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(item -> {
                            isLoading.setValue(true);
                        })
                        .doOnSuccess(item -> {
                            detailMutableLiveData.postValue(item);
                            isLoading.setValue(false);
                        })
                        .doOnError(error -> {
                            isLoading.setValue(false);
                        })
                        .subscribe()
        );
    }
}
