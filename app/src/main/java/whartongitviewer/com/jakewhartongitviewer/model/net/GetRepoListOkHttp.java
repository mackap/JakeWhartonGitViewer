package whartongitviewer.com.jakewhartongitviewer.model.net;


import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import whartongitviewer.com.jakewhartongitviewer.presenter.Presenter;
import whartongitviewer.com.jakewhartongitviewer.model.pojo.Reposit;

public class GetRepoListOkHttp implements Callback {
    private final String TAG = getClass().getSimpleName();

    OkHttpClient client;
    Request request;
    Call call;
    List<Reposit> repositoryList;

    public GetRepoListOkHttp(OkHttpClient client) {
        this.client = client;
        request = new Request.Builder()
                .url(Net.BASE_URL)
                .get()
                .build();
        call = client.newCall(request);
        call.enqueue(this);
    }

    @Override
    public void onFailure(Call call, IOException e) {
        Log.d(TAG, "onFailure, IOException e=" + e.getMessage());
        returtErrorMessage(e.getMessage());
    }

    @Override
    public void onResponse(Call call, Response response) {
        String strResp = "";
        try {
            strResp = response.body().string();
            try {
              List<Reposit> repoListResponses = new Gson().fromJson(strResp, new TypeToken<List<Reposit>>(){}.getType());
                Presenter.getInstance().getRepoListCallback(repoListResponses);
            } catch (JsonParseException e) {
                Log.d(TAG, "onResponse(), JSONException, e=" + e.getMessage());
                returtErrorMessage(e.getMessage());
            }

        } catch (IOException e) {
            Log.d(TAG, "IOException, e=" + e.getMessage());
            returtErrorMessage(e.getMessage());
        }

    }

    private void returtErrorMessage(String message) {
         Presenter.getInstance().getRepoListCallback(message);
    }
}
