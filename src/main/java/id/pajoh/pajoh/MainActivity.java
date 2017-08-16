package id.pajoh.pajoh;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import static android.R.attr.description;

public class MainActivity extends Activity {

    WebView view;
    SwipeRefreshLayout swipe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //      WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                LoadWeb();
            }
        });

        LoadWeb();
    }

    public void LoadWeb(){

        view = (WebView) this.findViewById(R.id.webView);
        WebSettings webSettings = view.getSettings();
        webSettings.setJavaScriptEnabled(true);
        swipe.setRefreshing(true);
        view.setWebViewClient(new MyBrowser() {

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

                view.loadUrl("file:///android_asset/error.html");

            }


            public  void  onPageFinished(WebView view, String url){

                //Hide the SwipeReefreshLayout

                swipe.setRefreshing(false);
            }


        });
        view.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        view.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        view.getSettings().setAppCacheEnabled(true);
        view.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setUseWideViewPort(true);
        webSettings.setSavePassword(true);
        webSettings.setSaveFormData(true);
        webSettings.setEnableSmoothTransition(true);

        view.loadUrl("https://pajoh.id");
        view.setWebChromeClient(new WebChromeClient());
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            view.loadUrl(url);
            return true;
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //ketika disentuh tombol back
        if ((keyCode == KeyEvent.KEYCODE_BACK) && view.canGoBack()) {
            view.goBack(); //method goback() dieksekusi untuk kembali pada halaman sebelumnya
            return true;
        }
        // Jika tidak ada history (Halaman yang sebelumnya dibuka)
        // maka akan keluar dari activity
        return super.onKeyDown(keyCode, event);
    }
}
