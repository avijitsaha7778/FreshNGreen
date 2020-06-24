package com.freshngreen.freshngreen;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Util {

  public static void showMessage(Context context, View view, String msg, int snackColor){

      Snackbar snackbar = Snackbar.make(view,msg,Snackbar.LENGTH_LONG);
      View snackbarView = snackbar.getView();
      TextView tv = (TextView) snackbarView.findViewById(R.id.snackbar_text);
      tv.setTextColor(Color.WHITE);

      snackbarView.setBackgroundColor(snackColor);
      snackbar.show();
  }
}
