package ca.com.androidbinnersproject.helpers;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by leodeleon on 04/01/2017.
 */

public class SharedPreferencesHelper {

  private final String file = "global_preferences";

  private static SharedPreferencesHelper instance = null;
  private Context context;

  public static SharedPreferencesHelper getInstance(Context context) {
    if (instance == null) {
      instance = new SharedPreferencesHelper();
    }
    instance.context = context;
    return instance;
  }

  public void putInt(String key, int value) {
    SharedPreferences sharedPref = context.getSharedPreferences(file, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPref.edit();
    editor.putInt(key, value);
    editor.apply();
  }

  public void putBoolean(String key, boolean value) {
    SharedPreferences sharedPref = context.getSharedPreferences(file, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPref.edit();
    editor.putBoolean(key, value);
    editor.apply();
  }

  public void putString(String key, String value) {
    SharedPreferences sharedPref = context.getSharedPreferences(file, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPref.edit();
    editor.putString(key, value);
    editor.apply();
  }

  public int getInt(String key) {
    SharedPreferences sharedPref = context.getSharedPreferences(file, Context.MODE_PRIVATE);
    return sharedPref.getInt(key, 0);
  }

  public boolean getBoolean(String key) {
    SharedPreferences sharedPref = context.getSharedPreferences(file, Context.MODE_PRIVATE);
    return sharedPref.getBoolean(key, false);
  }

  public String getString(String key) {
    SharedPreferences sharedPref = context.getSharedPreferences(file, Context.MODE_PRIVATE);
    return sharedPref.getString(key, null);
  }
}
