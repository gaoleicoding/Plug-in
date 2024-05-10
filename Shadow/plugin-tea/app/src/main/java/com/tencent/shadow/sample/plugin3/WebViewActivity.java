package com.tencent.shadow.sample.plugin3;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
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
        mWebview.setWebViewClient(new MyWebViewClient());
        initSetting(mWebview);
        initChromeClient();
        String url = getIntent().getStringExtra("url");
        Log.e(TAG, "tea WebViewActivity url:" + url);
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
                mTvTitle.setText("教师端：" + title);
            }


            //获取加载进度
            @Override
            public void onProgressChanged(WebView view, int newProgress) {

            }
        });
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            if (request != null && request.getUrl() != null) {
                final Uri uri = request.getUrl();
                String url = uri.toString();
                Log.e(TAG, "url: " + url);
                Log.e(TAG, "getScheme: " + uri.getScheme());
                Log.e(TAG, "getHost: " + uri.getHost());
                Log.e(TAG, "getPath: " + uri.getPath());
                Log.e(TAG, "getQueryParameterNames: " + uri.getQueryParameterNames());
                String filterUrl = "https://assess.fifedu.com/testcenter/webapp/viewExamPaper4js";

                if (url.contains(filterUrl)) {
                    Log.e(TAG, "skipToStu url: " + url);
                    Intent intent = new Intent();
                    intent.setAction("HOST_BROADCAST_ACTION");
                    intent.putExtra("PLUGIN_KEY", Constant.FROM_PLUGIN_KEY_SKIP_TO_STU_WEB);
                    intent.putExtra("PLUGIN_VALUE", url);
                    sendBroadcast(intent);
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            // 在主线程上更新UI的操作
                            if (mWebview.canGoBack()) {
                                view.goBack();
                            }
                        }
                    });
                }
            }

            // 这里会返回 null 就是不拦截了
            return super.shouldInterceptRequest(view, request);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebview.canGoBack()) {
            mWebview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}