package app.cave.newsapi.api;

public class ApiUtils {
    private static final String BASE_URL = "https://newsapi.org/";

    public ApiUtils() {
    }
    public static ApiService getService()
    {
        return ApiClient.getClient(BASE_URL).create(ApiService.class);
    }
}
