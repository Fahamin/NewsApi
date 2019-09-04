package app.cave.newsapi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;

import java.util.ArrayList;
import java.util.List;

import app.cave.newsapi.R;
import app.cave.newsapi.adapter.ArticleAdapter;
import app.cave.newsapi.api.ApiService;
import app.cave.newsapi.api.ApiUtils;
import app.cave.newsapi.constans.AppConstant;
import app.cave.newsapi.itemInerface.ItemClickListener;
import app.cave.newsapi.model.Article;
import app.cave.newsapi.model.GetNewsView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ItemClickListener {


    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;

    List<String> API_LIST = new ArrayList<>();
    List<Article> articleList = new ArrayList<>();
    ApiService apiService;

    int c = 0;
    private InterstitialAd interstitial;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        recyclerView = findViewById(R.id.recyclerView);
        swipeRefreshLayout = findViewById(R.id.refreshLayout);

        initApiList();

        swipeRefreshLayout.setRefreshing(true);
        loadNews();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadNews();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        adView = new AdView(this, getString(R.string.fb_banner), AdSize.BANNER_HEIGHT_50);
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container);
        adContainer.addView(adView);
        adView.loadAd();

    }


    private void initApiList() {

        API_LIST.add(AppConstant.INSTANCE.getAPI_1());
        API_LIST.add(AppConstant.INSTANCE.getAPI_2());
        API_LIST.add(AppConstant.INSTANCE.getAPI_3());
        API_LIST.add(AppConstant.INSTANCE.getAPI_4());
        API_LIST.add(AppConstant.INSTANCE.getAPI_5());
        API_LIST.add(AppConstant.INSTANCE.getAPI_6());
        API_LIST.add(AppConstant.INSTANCE.getAPI_7());
        API_LIST.add(AppConstant.INSTANCE.getAPI_8());
        API_LIST.add(AppConstant.INSTANCE.getAPI_9());
        API_LIST.add(AppConstant.INSTANCE.getAPI_10());
        API_LIST.add(AppConstant.INSTANCE.getAPI_11());
        API_LIST.add(AppConstant.INSTANCE.getAPI_12());
        API_LIST.add(AppConstant.INSTANCE.getAPI_13());
        API_LIST.add(AppConstant.INSTANCE.getAPI_14());
        API_LIST.add(AppConstant.INSTANCE.getAPI_15());
        API_LIST.add(AppConstant.INSTANCE.getAPI_16());
        API_LIST.add(AppConstant.INSTANCE.getAPI_17());
        API_LIST.add(AppConstant.INSTANCE.getAPI_18());
        API_LIST.add(AppConstant.INSTANCE.getAPI_19());
        API_LIST.add(AppConstant.INSTANCE.getAPI_20());
        API_LIST.add(AppConstant.INSTANCE.getAPI_21());
        API_LIST.add(AppConstant.INSTANCE.getAPI_22());
        API_LIST.add(AppConstant.INSTANCE.getAPI_23());
        API_LIST.add(AppConstant.INSTANCE.getAPI_24());
        API_LIST.add(AppConstant.INSTANCE.getAPI_25());
        API_LIST.add(AppConstant.INSTANCE.getAPI_26());
        API_LIST.add(AppConstant.INSTANCE.getAPI_27());
        API_LIST.add(AppConstant.INSTANCE.getAPI_28());
        API_LIST.add(AppConstant.INSTANCE.getAPI_29());
        API_LIST.add(AppConstant.INSTANCE.getAPI_30());
        API_LIST.add(AppConstant.INSTANCE.getAPI_31());
        API_LIST.add(AppConstant.INSTANCE.getAPI_32());
        API_LIST.add(AppConstant.INSTANCE.getAPI_33());
        API_LIST.add(AppConstant.INSTANCE.getAPI_34());
    }

    private void loadNews() {

        apiService = ApiUtils.getService();

        for (int i = 0; i < 32; i++) {
            Load(API_LIST.get(i));
        }

    }

    private void Load(String url) {
        articleList.clear();
        apiService.getNewsDetails(url + AppConstant.INSTANCE.getAPI_KEY()).enqueue(new Callback<GetNewsView>() {
            @Override
            public void onResponse(Call<GetNewsView> call, Response<GetNewsView> response) {
                if (response.isSuccessful()) {
                    if (response.body().getArticles() != null) {
                        articleList.addAll(response.body().getArticles());
                        ArticleAdapter adapter = new ArticleAdapter(DetailsActivity.this, articleList, recyclerView, DetailsActivity.this);
                        recyclerView.setLayoutManager(new LinearLayoutManager(DetailsActivity.this));
                        // recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                        adapter.notifyDataSetChanged();
                        recyclerView.setAdapter(adapter);
                    }
                }
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<GetNewsView> call, Throwable t) {

                Toast.makeText(DetailsActivity.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }


    void addShow() {
        c++;
        if (c % 2 == 0) {
            interstitial = new com.facebook.ads.InterstitialAd(this, getString(R.string.facebook_interstial));
            interstitial.setAdListener(new InterstitialAdListener() {
                @Override
                public void onInterstitialDisplayed(Ad ad) {

                }

                @Override
                public void onInterstitialDismissed(Ad ad) {

                }

                @Override
                public void onError(Ad ad, AdError adError) {

                }

                @Override
                public void onAdLoaded(Ad ad) {
                    showInterstitial();
                }

                @Override
                public void onAdClicked(Ad ad) {

                }

                @Override
                public void onLoggingImpression(Ad ad) {

                }
            });
            interstitial.loadAd();

        }
    }

    void showInterstitial() {
        if(interstitial.isAdLoaded())
        {
            interstitial.show();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Load(API_LIST.get(1));
            addShow();
        } else if (id == R.id.nav_slideshow) {
            Load(API_LIST.get(5));
            addShow();
        } else if (id == R.id.nav_manage) {

            Load(API_LIST.get(2));
            addShow();
        } else if (id == R.id.cnnID) {
            Load(API_LIST.get(10));
            addShow();

        } else if (id == R.id.cryptoID) {
            Load(API_LIST.get(11));
            addShow();

        } else if (id == R.id.dailymailID) {
            Load(API_LIST.get(12));
            addShow();

        } else if (id == R.id.espnID) {
            Load(API_LIST.get(13));
            addShow();

        } else if (id == R.id.foxID) {
            Load(API_LIST.get(3));
            addShow();

        } else if (id == R.id.gurdianID) {
            Load(API_LIST.get(9));
            addShow();

        } else if (id == R.id.neyworktimeID) {
            Load(API_LIST.get(19));
            addShow();

        } else if (id == R.id.wasintonPost) {
            Load(API_LIST.get(7));
            addShow();

        } else if (id == R.id.usaToday) {
            Load(API_LIST.get(6));
            addShow();

        } else if (id == R.id.newMagineID) {
            Load(API_LIST.get(18));
            addShow();
        } else if (id == R.id.wallStreet) {
            Load(API_LIST.get(8));
            addShow();
        }
        else if(id==R.id.australiaID)
        {
            Load(API_LIST.get(20));
            addShow();
        }
        else if(id==R.id.braxilID)
        {
            Load(API_LIST.get(21));
            addShow();
        }
        else if(id==R.id.mexicoID)
        {
            Load(API_LIST.get(22));
            addShow();
        }
        else if(id==R.id.newzelanID)
        {
            Load(API_LIST.get(23));
            addShow();
        }
        else if(id==R.id.rausiaID)
        {
            Load(API_LIST.get(24));
            addShow();
        }
        else if(id==R.id.usaID)
        {
            Load(API_LIST.get(25));
            addShow();
        }
        else if(id==R.id.chinaID)
        {
            Load(API_LIST.get(26));
            addShow();
        }
        else if(id==R.id.uaeID)
        {
            Load(API_LIST.get(27));
            addShow();
        }
        else if(id==R.id.egyptID)
        {
            Load(API_LIST.get(28));
            addShow();
        }
        else if(id==R.id.southaffircaID)
        {
            Load(API_LIST.get(29));
            addShow();
        }
        else if(id==R.id.japanID)
        {
            Load(API_LIST.get(30));
            addShow();
        }
        else if(id==R.id.venuzulaID)
        {
            Load(API_LIST.get(31));
            addShow();
        }
        else if(id==R.id.turkeyID)
        {
            Load(API_LIST.get(32));
            addShow();
        }
        else if(id==R.id.suadiID)
        {
            Load(API_LIST.get(33));
            addShow();
        }

        else if (id == R.id.nav_share) {
            Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Install now");
            String app_url = "https://play.google.com/store/apps/details?id=app.cave.newsapi";
            shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, app_url);
            startActivity(Intent.createChooser(shareIntent, "Share via"));
            return true;

        }

      /*  else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void itemCick(int position) {
        addShow();
        String link = articleList.get(position).getUrl();

     //   String link = article.getUrl();
        startActivity(new Intent(DetailsActivity.this,web.class).putExtra("url",link));

    /*startActivity(new Intent(DetailsActivity.this, MainActivity.class)
                .putExtra("article", articleList.get(position)));*/

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Install now");
            String app_url = "https://play.google.com/store/apps/details?id=app.cave.newsapi";
            shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, app_url);
            startActivity(Intent.createChooser(shareIntent, "Share via"));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
