package ca.com.androidbinnersproject.util;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Created by jonathan_campos on 06/03/2016.
 */
public class SuggestionProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = "ca.com.androidbinnersproject";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public SuggestionProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}
