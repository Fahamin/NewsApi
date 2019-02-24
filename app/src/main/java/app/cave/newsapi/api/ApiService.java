package app.cave.newsapi.api;

import app.cave.newsapi.model.GetNewsView;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ApiService {

    @GET
    Call<String> getNumberInfo(@Url String url);

    @GET
    Call<GetNewsView>getNewsDetails(@Url String url);
}
