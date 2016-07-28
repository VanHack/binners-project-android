package ca.com.androidbinnersproject.util;

import android.content.Context;

/**
 * Created by jonathan_campos on 28/07/2016.
 *
 * Class that store an application Context which will be used by the system and also
 *  provide methods to store and retrive Shared Preferences
 *
 */
public class BinnersSettings {

    private static Context mContext;
    private static final String app = "binners-app";

    private static final String SHARED_TOKEN = "TOKEN";
    private static final String PROFILE_NAME = "PROFILE_NAME";
    private static final String PROFILE_EMAIL= "PROFILE_EMAIL";


    public static void initialize(final Context context) {
        mContext = context;
    }

    public static Context getSystemContext() {
        return mContext;
    }

    public static boolean setToken(final String token) {
        return getSystemContext().getSharedPreferences(app, Context.MODE_PRIVATE).edit().putString(SHARED_TOKEN, token).commit();
    }

    public static String getToken() {
        return getSystemContext().getSharedPreferences(app, Context.MODE_PRIVATE).getString(SHARED_TOKEN, "");
    }

    public static boolean setProfileName(final String token) {
        return getSystemContext().getSharedPreferences(app, Context.MODE_PRIVATE).edit().putString(PROFILE_NAME, token).commit();
    }

    public static String getProfileName() {
        return getSystemContext().getSharedPreferences(app, Context.MODE_PRIVATE).getString(PROFILE_NAME, "");
    }

    public static boolean setProfileEmail(final String token) {
        return getSystemContext().getSharedPreferences(app, Context.MODE_PRIVATE).edit().putString(PROFILE_EMAIL, token).commit();
    }

    public static String getProfileEmail() {
        return getSystemContext().getSharedPreferences(app, Context.MODE_PRIVATE).getString(PROFILE_EMAIL, "<>");
    }
}
