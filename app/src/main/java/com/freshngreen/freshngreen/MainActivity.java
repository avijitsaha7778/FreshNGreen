package com.freshngreen.freshngreen;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private ProgressBar progressBar;
    private ProgressDialog waitDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = (WebView)findViewById(R.id.webView);
        progressBar = (ProgressBar) findViewById(R.id.progress);

        if (NetworkUtils.isNetworkAvailable(MainActivity.this))
        {
            WebSettings webSettings = webView.getSettings();
            webView.getSettings().setLoadsImagesAutomatically(true);
            webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            webView.setWebChromeClient( new MyWebChromeClient());
            webView.setWebViewClient( new webClient());
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.getSettings().setSupportZoom(true);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl("http://freshngreen.co.in");
            showWaitDialog("Loading...");

        }
        else
        {
            try {
                showErrorMsg(getResources().getString(R.string.no_connection_msg));
            }catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

    private void showErrorMsg(String msg) {
        Util.showMessage(MainActivity.this,getView(),msg,getResources().getColor(R.color.snack_red));
    }
    private void showSuccessMsg(String msg) {
        Util.showMessage(MainActivity.this,getView(),msg,getResources().getColor(R.color.snack_green));
    }
    private View getView(){
        View parentLayout = findViewById(android.R.id.content);
        return parentLayout;
    }

    public class MyWebChromeClient extends WebChromeClient {
        public void onProgressChanged(WebView view, int newProgress) {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(newProgress);
//            showWaitDialog("Loading...");
        }
    }

    public class webClient extends WebViewClient {
        public boolean  shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
            if (waitDialog.isShowing()) {
                closeWaitDialog();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
            progressBar.setVisibility(View.GONE);
            if (waitDialog.isShowing()) {
                closeWaitDialog();
            }
        } else  {
            super.onBackPressed();
        }
    }

    private void showWaitDialog(String message) {
        try {
            waitDialog = new ProgressDialog(MainActivity.this, R.style.AppCompatAlertDialogStyle);
            waitDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            waitDialog.setMessage(message);
            waitDialog.setIndeterminate(true);
            waitDialog.setCancelable(false);
            waitDialog.show();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void closeWaitDialog() {
        try {
            waitDialog.dismiss();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}