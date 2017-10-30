package whartongitviewer.com.jakewhartongitviewer.model.net;


import okhttp3.OkHttpClient;

public class Net {

    static final String BASE_URL = "https://api.github.com/users/JakeWharton/repos?per_page=100";
    private static OkHttpClient okHttpClient;

    public static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient();
        }
        return okHttpClient;
    }
}
