package com.tencent.shadow.sample.plugin2;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;


public class WebViewActivity extends Activity {
    private final String TAG = "WebViewActivity";
    private WebView mWebview;
    private TextView mTvTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        mWebview = findViewById(R.id.webview);
        mTvTitle = findViewById(R.id.tv_title);
        mWebview.setWebViewClient(new WebViewClient());
        initSetting(mWebview);
        initChromeClient();
        String url = getIntent().getStringExtra("url");
        Log.e(TAG, "stu WebViewActivity url:" + url);
        mWebview.loadUrl(url);
    }

    private void initSetting(WebView webView) {
        WebSettings mWebSettings = webView.getSettings();

        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        mWebSettings.setJavaScriptEnabled(true);//设置支持javaScript
        mWebSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        mWebSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//        mWebSettings.setUserAgentString("User-Agent");
        mWebSettings.setLightTouchEnabled(true);//设置用鼠标激活被选项
        mWebSettings.setBuiltInZoomControls(true);//设置支持缩放
        mWebSettings.setDomStorageEnabled(true);//设置DOM缓存，当H5网页使用localStorage时，一定要设置
        mWebSettings.setDatabaseEnabled(true);
        mWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);//设置去缓存，防止加载的为上一次加载过的
        mWebSettings.setSupportZoom(true);//设置支持变焦
        webView.setHapticFeedbackEnabled(false);
        mWebSettings.setPluginState(WebSettings.PluginState.ON);
        mWebSettings.setAllowFileAccess(true);
        mWebSettings.setAllowContentAccess(true);
        mWebSettings.setAllowUniversalAccessFromFileURLs(true);
        mWebSettings.setAllowFileAccessFromFileURLs(true);
        mWebSettings.setFixedFontFamily("cursive");
    }

    private void initChromeClient() {
        //设置WebChromeClient类
        mWebview.setWebChromeClient(new WebChromeClient() {

            //获取网站标题
            @Override
            public void onReceivedTitle(WebView view, String title) {
                mTvTitle.setText(title);
            }


            //获取加载进度
            @Override
            public void onProgressChanged(WebView view, int newProgress) {

            }
        });
    }

}