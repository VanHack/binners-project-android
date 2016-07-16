
package ca.com.androidbinnersproject.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import ca.com.androidbinnersproject.activities.LoginActivity;

public class Util {
	private static ProgressDialog pDialog;

	public static byte[] HexStringToByteArray(String s)
	{
		int len = s.length();
		byte[] data = new byte[len / 2];

		for(int i = 0; i < len; i += 2)
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));

		return data;
	}

	public static boolean hasInternetConnection(Context context) {
		ConnectivityManager cm =
				(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		return activeNetwork != null &&
				activeNetwork.isConnectedOrConnecting();
	}

	public static boolean isEmailValid(String email) {
		String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
		java.util.regex.Matcher m = p.matcher(email);
		return m.matches();
	}

	public static String getUserLogged(Activity activity) {
		SharedPreferences preferences = activity.getSharedPreferences(LoginActivity.USER_AUTHENTICATED, 0);

		String profile = preferences.getString(LoginActivity.PROFILE_NAME, "");

		if(profile != null && profile.length() > 0)
			return profile;

		return preferences.getString(LoginActivity.PROFILE_EMAIL, "<>");
	}

	public static String getStringResource(Context context, int resId) {
        return context.getString(resId);
    }

	public static String getTimeFormated(Calendar cal) {
		if(cal != null) {
			return new SimpleDateFormat("HH:mm").format(cal.getTime());
		}
		return "";
	}

	public static String getDateFormated(Calendar cal) {
		if(cal != null) {
			return new SimpleDateFormat("dd/MM/yyyy").format(cal.getTime());
		}
		return "";
	}

	public static void showProgressDialog(final Context context, final String title, final String message) {
		pDialog  = new ProgressDialog(context);
		pDialog.setTitle(title);
		pDialog.setMessage(message);
		pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pDialog.show();
	}

	public static void dismissProgressDialog() {
		if(pDialog != null && pDialog.isShowing())
			pDialog.dismiss();
	}
}
