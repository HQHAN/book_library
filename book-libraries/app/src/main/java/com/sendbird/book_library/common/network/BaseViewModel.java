package com.sendbird.book_library.common.network;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;

public class BaseViewModel extends ViewModel {
    protected CompositeDisposable compositeDisposable = new CompositeDisposable();
    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
