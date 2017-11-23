package whartongitviewer.com.jakewhartongitviewer.model.net;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;
import whartongitviewer.com.jakewhartongitviewer.model.pojo.Reposit;



public interface NetworkAPI {
     interface GitHubService{
        @GET("users/{user}/repos")
        Call<List<Reposit>> getRepos(@Path("user") String user);

        /**
         *
         * @param url next page with repos
         * @return List Reposit
         */
        @GET
        Call<List<Reposit>> getNextUserRepo(@Url String url);
    }
}
