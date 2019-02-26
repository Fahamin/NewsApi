package app.cave.newsapi.activity;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import app.cave.newsapi.R;

public class web extends AppCompatActivity {

    private WebView webView;
    String string;
    SwipeRefreshLayout refreshLayout;
    private Activity mActivity;
    private ProgressDialog progressDialog;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_web);

        MobileAds.initialize(this, getString(R.string.appID));

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        
        string = getIntent().getStringExtra("url");

       /* CountDownTimer c = new CountDownTimer(2000, 0) {
            @Override
            public void onTick(long millisUntilFinished) {
                progressDialog = new ProgressDialog(web.this);
                progressDialog.setMessage("Loading data. Please wait....");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }

            @Override
            public void onFinish() {
                progressDialog.dismiss();
            }
        };*/


        wevViewfunction();
        refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(false);
            }
        });

    }

    private void wevViewfunction() {

        webView = (WebView) findViewById(R.id.webViewID);


        webView.setWebViewClient(new browser());
        webView.setWebChromeClient(new GoogleClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.loadUrl(string);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().getDefaultFontSize();
        webView.clearCache(true);
        webView.clearHistory();
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {

              /*  mInterstitialAd = new InterstitialAd(MainActivity.this);
                mInterstitialAd.setAdUnitId(getString(R.string.interstitial_full_screen1));
                AdRequest adRequest = new AdRequest.Builder().build();
                mInterstitialAd.loadAd(adRequest);
                mInterstitialAd.setAdListener(new AdListener() {
                    public void onAdLoaded() {
                        showInterstitial();
                    }
                });*/

                DownloadManager.Request request = new
                        DownloadManager.Request(Uri.parse(url));
                request.setMimeType(mimetype);
                String cookies = CookieManager.getInstance().getCookie(url);
                request.addRequestHeader("cookie", cookies);
                request.addRequestHeader("User-Agent", userAgent);
                request.setDescription("Downloading file...");
                request.setTitle(URLUtil.guessFileName(url, contentDisposition,
                        mimetype));
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalFilesDir(web.this,
                        Environment.DIRECTORY_DOWNLOADS, ".pdf");

                DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                dm.enqueue(request);

                Toast.makeText(getApplicationContext(), "Downloading File",
                        Toast.LENGTH_LONG).show();

            }
        });

        webView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* mInterstitialAd = new InterstitialAd(MainActivity.this);
                mInterstitialAd.setAdUnitId(getString(R.string.interstitial_full_screen1));
                AdRequest adRequest = new AdRequest.Builder().build();
                mInterstitialAd.loadAd(adRequest);
                mInterstitialAd.setAdListener(new AdListener() {
                    public void onAdLoaded() {
                        showInterstitial();
                    }
                });*/
                webView.loadUrl(string);
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.web_menu, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            /*case android.R.id.home:
                return true;
*/
            case R.id.goBack:
                /*if (mRewardedVideoAd.isLoaded()) {
                    mRewardedVideoAd.show();
                }*/
                if (webView.canGoBack()) {
                    webView.goBack();
                }
                break;

            case R.id.goForward:
               /* if (mRewardedVideoAd.isLoaded()) {
                    mRewardedVideoAd.show();
                }*/
                if (webView.canGoForward()) {
                    webView.goForward();
                }
                break;
            case R.id.reload:
               /* if (mRewardedVideoAd.isLoaded()) {
                    mRewardedVideoAd.show();
                }*/
                webView.loadUrl(webView.getUrl().toString());
                break;
            case R.id.share:
                Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Install now");
                String app_url = "https://play.google.com/store/apps/details?id=app.cave.newsapi";
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, app_url);
                startActivity(Intent.createChooser(shareIntent, "Share via"));
                break;
            case R.id.rate:
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=app.cave.newsapi"));
                startActivity(intent);
                break;
            case R.id.moreapp:
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/dev?id=7556100466882241730"));
                startActivity(i);
                break;
            case R.id.exit:
                finish();
                System.exit(0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public class browser extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

   /* private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }*/

    private class GoogleClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }
    }
}
