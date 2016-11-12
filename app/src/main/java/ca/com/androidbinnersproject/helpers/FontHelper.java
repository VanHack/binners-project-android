package ca.com.androidbinnersproject.helpers;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by dev on 11/12/2016.
 */

public class FontHelper {

  public static Typeface getTypeface(Context context, int textStyle) {
  Typeface typeface = null;

  switch (textStyle) {
    case 1:
      typeface = Typeface.createFromAsset(context.getAssets(), "Lato-Regular.ttf");
      break;
    case 2:
      typeface = Typeface.createFromAsset(context.getAssets(), "Lato-Bold.ttf");
      break;
    case 3:
      typeface = Typeface.createFromAsset(context.getAssets(), "Lato-Black.ttf");
      break;
    default:
      typeface = Typeface.createFromAsset(context.getAssets(), "Lato-Regular.ttf");
  }
  return typeface;
}
}
