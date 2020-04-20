package com.sendbird.book_library.search.ui;

import android.content.SearchRecentSuggestionsProvider;

public class SearchHistoryProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = "com.sendbird.book_library.search.ui.SearchHistoryProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public SearchHistoryProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}
