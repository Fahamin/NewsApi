package app.cave.newsapi.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import app.cave.newsapi.itemInerface.ItemClickListener;
import app.cave.newsapi.R;
import app.cave.newsapi.model.Article;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.articleholder> {

    Context context;
    List<Article> articleList;
    ItemClickListener itemClickListener;
    RecyclerView recyclerView;
    Activity activity;

    public ArticleAdapter(Context context, List<Article> articleList, RecyclerView recyclerView, Activity activity) {
        this.context = context;
        this.articleList = articleList;
        this.recyclerView = recyclerView;
        itemClickListener = (ItemClickListener) (activity);
    }


    @NonNull
    @Override
    public articleholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.newslayout, null, false);
        return new articleholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull articleholder articleholder, int i) {
        Glide.with(context).load(articleList.get(i).getUrlToImage()).apply(new RequestOptions()
                .placeholder(R.drawable.newspaper).error(R.drawable.newspaper))
                .into(articleholder.newsImage);

        articleholder.newsTitle.setText(articleList.get(i).getTitle());
        articleholder.publishedTime.setText(articleList.get(i).getPublishedAt());


    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public class articleholder extends RecyclerView.ViewHolder {
        ImageView newsImage;
        TextView newsTitle, publishedTime;

        public articleholder(@NonNull View itemView) {
            super(itemView);
            newsImage = itemView.findViewById(R.id.newsImage);
            newsTitle = itemView.findViewById(R.id.newsTitle);
            publishedTime = itemView.findViewById(R.id.publishedTime);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.itemCick(getAdapterPosition());
                }
            });
        }
    }
}
