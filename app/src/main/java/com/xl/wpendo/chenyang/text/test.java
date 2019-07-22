package com.xl.wpendo.chenyang.text;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * created by chenyang
 * on 2019/7/20
 */
public class test {
    private Context context;
    LinearLayout.LayoutParams firstEditParam = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

    EditText editText = new EditText(context);
    LinearLayout linearLayout = new LinearLayout(context);

    public test(LinearLayout linearLayout) {
        this.linearLayout = linearLayout;
        linearLayout.addView(editText, firstEditParam);
        editText.setText("ffff");
    }

    public void getBitmap(String patj) {
        Bitmap bitmap;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}