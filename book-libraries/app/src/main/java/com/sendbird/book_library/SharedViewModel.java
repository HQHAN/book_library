package com.sendbird.book_library;

import androidx.lifecycle.MutableLiveData;

import com.sendbird.book_library.common.network.BaseViewModel;
import com.sendbird.book_library.model.home.BookList.Book;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SharedViewModel extends BaseViewModel {
    public Set<Long> bookMarkedIsbnSet = new HashSet<>();
    public Set<Long> visitedBookIsbnSet = new HashSet<>();

    public List<Book> bookList = new ArrayList<>();

    public void setNewBookList(List<Book> list) {
        bookList.clear();
        bookList.addAll(list);
    }

    public void addBookMarkItem(Long isbn) {
        bookMarkedIsbnSet.add(isbn);
    }

    public void removeBookMarkItem(Long isbn) {
        bookMarkedIsbnSet.remove(isbn);
    }

    public boolean isBookMarked(Long isbn) {
        return bookMarkedIsbnSet.contains(isbn);
    }

    public boolean isVisitedBook(Long isbn) {
        return visitedBookIsbnSet.contains(isbn);
    }

    public void markVisitedBookDetail(Long isbn) {
        visitedBookIsbnSet.add(isbn);
    }

    public List<Book> getBookMarkList() {
        return bookList.stream()
                .filter(book -> isBookMarked(book.isbn13))
                .collect(Collectors.toList());
    }

    public List<Book> getVisitedHistoryList() {
        return bookList.stream()
                .filter(book -> isVisitedBook(book.isbn13))
                .collect(Collectors.toList());
    }
}
