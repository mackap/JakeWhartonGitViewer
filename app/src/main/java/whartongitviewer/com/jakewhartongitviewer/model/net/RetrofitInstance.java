package whartongitviewer.com.jakewhartongitviewer.model.net;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitInstance {
private final static String BASE_URL = "https://api.github.com/";

    private static Retrofit retrofit;
    public static Retrofit getRetrofit(){
        if(retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
