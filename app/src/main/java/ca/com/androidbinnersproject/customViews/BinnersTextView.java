package ca.com.androidbinnersproject.customViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import ca.com.androidbinnersproject.R;
import ca.com.androidbinnersproject.helpers.FontHelper;

/**
 * Created by dev on 11/12/2016.
 */

public class BinnersTextView extends TextView {
  public BinnersTextView(Context context) {
    super(context);
  }

  public BinnersTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context, attrs);
  }

  public BinnersTextView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context, attrs);
  }

  private void init(Context context, AttributeSet attrs) {
    TypedArray atr = context.getTheme().obtainStyledAttributes(attrs,R.styleable.BinnersTextView, 0, 0);
    int textStyle = atr.getInt(R.styleable.BinnersTextView_btv_textStyle, 2);
    atr.recycle();
    setTypeface(FontHelper.getTypeface(getContext(), textStyle));
  }
}
