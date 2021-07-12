package com.example.nubankdesigntest;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.os.Bundle;

public class ApplyLoan extends AppCompatActivity {
    private WebView gwebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_loan);

        gwebview=(WebView) findViewById(R.id.gformwebview);
        gwebview.setWebViewClient(new WebViewClient());
        gwebview.loadUrl("https://docs.google.com/forms/d/e/1FAIpQLSdnqsYgobHEd2fA-zyHlHWC-fBm445XoSui82uLg3K16IbWcQ/viewform");
        WebSettings webSettings=gwebview.getSettings();
        webSettings.setJavaScriptEnabled(true);



    }

    public class gwebview extends WebViewClient{
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon){
            super.onPageStarted(view,url,favicon);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view,String url){
            view.loadUrl(url);
            return true;
        }
    }
    @Override
    public void onBackPressed(){
        if(gwebview.canGoBack()) {
            gwebview.goBack();
        }
        else{
            super.onBackPressed();
        }
    }
}