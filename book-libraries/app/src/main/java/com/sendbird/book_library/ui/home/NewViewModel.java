package com.sendbird.book_library.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sendbird.book_library.common.network.NetworkManager;
import com.sendbird.book_library.model.home.BookList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class NewViewModel extends ViewModel {
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public MutableLiveData<BookList> newBookList = new MutableLiveData<>();
    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }

    public void fetchNewBookList() {
        compositeDisposable.add(
                NetworkManager.getInstance().bookServiceApi.getNewBookList()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSuccess(item -> {
                        newBookList.setValue(item);
                    })
                    .doOnError(error -> {

                    })
                    .subscribe()
        );
    }
}