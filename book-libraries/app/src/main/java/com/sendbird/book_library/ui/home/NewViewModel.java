package com.sendbird.book_library.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sendbird.book_library.common.network.NetworkManager;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NewViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NewViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        testNetworkCall();
        return mText;
    }

    private void testNetworkCall() {
        NetworkManager.getInstance().bookServiceApi.getNewBookList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(item -> {
                    mText.setValue(item.books.get(0).title);
                })
                .doOnError(error -> {

                })
                .subscribe();
    }
}