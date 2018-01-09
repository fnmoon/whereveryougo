package com.example.whereveryougo;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Weather extends AppCompatActivity {
    private ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        WebView webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);// 可用JS\
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (url != null ) {
                    String fun = "javascript:function getClass(parent,sClass) { var aEle=parent.getElementsByTagName('div'); var aResult=[]; var i=0; for(i<0;i<aEle.length;i++) { if(aEle[i].className==sClass) { aResult.push(aEle[i]); } }; return aResult; } ";
                    view.loadUrl(fun);
                    String fun2 = "javascript:function hideOther() { getClass(document,'wrapper head-wrap')[0].style.display='none'; getClass(wrapper,'head-wrap')[0].style.display='none'; getClass(document,'head-wrap')[0].style.display='none'; getClass(document,'home-foot')[0].style.display='none'; getClass(document,'enter')[0].style.display='none'; getClass(document,'crumb')[0].style.display='none'; getClass(document,'date-tab clearfix')[0].style.display='none'; document.getElementById('head-wrap').style.display='none'; document.getElementById('wrapper head-wrap').style.display='none'; document.getElementById('fix-personal').style.display='none'; document.getElementById('waterlogo').style.display='none'; getClass(document,'wrap')[0].style.minWidth=0; getClass(document,'game')[0].style.paddingTop=0;}";
                    view.loadUrl(fun2);
                    view.loadUrl("javascript:hideOther();");
                }
                super.onPageFinished(view, url);
            }

        });
        webView.setScrollBarStyle(0);// 滚动条风格，为0就是不给滚动条留空间，滚动条覆盖在网页上
        webView.getSettings().setSupportZoom(true);// 支持缩放
        webView.getSettings().setBuiltInZoomControls(true);// 显示放大缩小[/mw_shl_code]
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    closeDialog();
                } else {
                    openDialog(newProgress);
                }
            }

            private void openDialog(int newProgress) {
                //如果对话框等于空，也就是没有对话框
                if (pd == null) {
                    //设定一个新的对话框
                    pd = new ProgressDialog(Weather.this);
                    //设置标题
                    pd.setTitle("加载中。。。");
                    pd.setIcon(R.mipmap.ic_launcher);
                    //设置对话框进度条样式，设置为横向
                    pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    //设置进度，随刷新进度改变
                    pd.setProgress(newProgress);
                    pd.show();

                } else {
                    pd.setProgress(newProgress);
                }
            }

            private void closeDialog()
            {
                if (pd != null && pd.isShowing())
                {
                    pd.dismiss();
                    pd = null;
                }
            }

        });
        webView.loadUrl("http://m.weather.com.cn/d/town/index?lat=23.12908&lon=113.26436");

    }
}


