package app.cave.newsapi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import app.cave.newsapi.R;
import app.cave.newsapi.model.Article;

public class MainActivity extends AppCompatActivity {

    TextView titleTextView;
    TextView authorTextView;
    TextView timeTextView;
    ImageView newsPhoto;
    TextView detailsTextView;
    TextView viewMore;
    private Article article;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        initViews();
        initObjects();
        setData();

    }
    private void initViews() {

        timeTextView = findViewById(R.id.timeTextView);
        titleTextView = findViewById(R.id.titleTextView);
        authorTextView = findViewById(R.id.authorTextView);
        newsPhoto = findViewById(R.id.newsPhoto);
        detailsTextView = findViewById(R.id.detailsTextView);
        viewMore = findViewById(R.id.viewMore);
        viewMore.setText(Html.fromHtml("<u>Read More </u>"));
    }

    private void initObjects() {
        article = (Article) getIntent().getSerializableExtra("article");
    }

    private void setData() {
        titleTextView.setText(article.getTitle());

        if (article.getAuthor() != null)
            authorTextView.setText(article.getAuthor().toString());
        timeTextView.setText(article.getPublishedAt());
        detailsTextView.setText(article.getDescription());

        Glide.with(this).load(article.getUrlToImage())
                .apply(new RequestOptions().placeholder(R.drawable.newspaper).error(R.drawable.newspaper))
                .into(newsPhoto);
    }
    public void viewMore(View view) {

        String link = article.getUrl();
        startActivity(new Intent(MainActivity.this,web.class).putExtra("url",link));
    }
}
