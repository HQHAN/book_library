package com.sendbird.book_library.common;

import com.sendbird.book_library.common.network.BaseViewModel;
import com.sendbird.book_library.home.model.BookList.Book;

import java.util.ArrayList;
import java.util.Comparator;
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

    public List<Book> getBookMarkList(Comparator<Book> comparator) {
        return bookList.stream()
                .filter(book -> isBookMarked(book.isbn13))
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    public List<Book> getVisitedHistoryList() {
        return bookList.stream()
                .filter(book -> isVisitedBook(book.isbn13))
                .collect(Collectors.toList());
    }
}
