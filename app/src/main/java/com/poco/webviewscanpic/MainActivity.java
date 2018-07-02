package com.poco.webviewscanpic;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.wv_webview);
        initWebConfig();
        webView.loadUrl("http://m.pocoimg.cn/works/works_detail?works_id=20001630&app_blog=1");

    }

    private void initWebConfig() {
        webView.addJavascriptInterface(new MJavascriptInterface(this), "imagelistener");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setDefaultTextEncodingName("utf-8");
        webSettings.setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }


            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                addImageClickListener(view);//待网页加载完全后设置图片点击的监听方法
            }
        });
    }

    /**
     * 设置图片点击事件
     *
     * @param webView
     */
    private void addImageClickListener(WebView webView) {
        //"cc_detail_blog_img"这个ClassName和前端对应的，前端那边不能修改
        webView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByClassName(\"cc_detail_blog_img\"); " +
                " var array=new Array(); " + " for(var j=0;j<objs.length;j++){ " + "array[j]=objs[j].src;" + " }  " +
                "for(var i=0;i<objs.length;i++)  " +
                "{" +
                "  objs[i].onclick=function()  " +
                "  {  " +
                "    window.imagelistener.openImage(this.src,array);  " +
                "  }  " +
                "}" +
                "})()");
    }
}
