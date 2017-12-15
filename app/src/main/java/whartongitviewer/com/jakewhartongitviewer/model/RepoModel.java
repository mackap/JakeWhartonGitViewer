package whartongitviewer.com.jakewhartongitviewer.model;


import java.util.List;

import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import whartongitviewer.com.jakewhartongitviewer.model.net.NetworkAPI;
import whartongitviewer.com.jakewhartongitviewer.model.net.RetrofitInstance;
import whartongitviewer.com.jakewhartongitviewer.model.pojo.Reposit;
import whartongitviewer.com.jakewhartongitviewer.presenter.IRepoPresenter;

public class RepoModel implements IRepoModel {
    private final String TAG = getClass().getSimpleName();
    List<Reposit> repoList;
    IRepoPresenter repoPresenter;
    private final String HEADER_LINK = "Link";
    private final String NEXT_LINK = "next";
    String nextLink;
    private final String USER = "JakeWharton";


    private NetworkAPI.GitHubService api;

    public RepoModel(IRepoPresenter repoPresenter) {
        this.repoPresenter = repoPresenter;
        api = RetrofitInstance.getRetrofit().create(NetworkAPI.GitHubService.class);
    }

    @Override
    public List<Reposit> getRepoList() {
        return repoList;
    }

    @Override
    public void loadRepos(IRepoPresenter.LoadRepoCallback loadReposCallback) {
        Call<List<Reposit>> repoListCall;

        //// check if need load next page data or it is first load
        if (nextLink == null) {
            repoListCall = api.getRepos(USER);
        } else {
            repoListCall = api.getNextUserRepo(nextLink);
        }

        repoListCall.enqueue(new Callback<List<Reposit>>() {
            @Override
            public void onResponse(Call<List<Reposit>> call, Response<List<Reposit>> response) {
                nextLink = getNextLinkFromGitHub(response.headers());
                addRepos(response.body());
                loadReposCallback.onSuccess();
            }

            @Override
            public void onFailure(Call<List<Reposit>> call, Throwable t) {
                loadReposCallback.onError(t.getMessage());
            }
        });
    }

    @Override
    public void addRepos(List<Reposit> addRepositList) {
        if (repoList == null) {
            repoList = addRepositList;
        } else {
            repoList.addAll(addRepositList);
        }
    }


    @Override
    public Reposit getRepoFromId(long currentRepoId) {

        if (repoList != null) {
            for (Reposit repos : repoList) {
                if (repos.getId() == currentRepoId) {
                    return repos;
                }
            }
        }
        return null;
    }

    /**
     * @param headers from responce
     * @return url next page with data if it is exist
     */
    public String getNextLinkFromGitHub(Headers headers) {
        String linkFromGitHub = null;
        if (headers != null) {
            linkFromGitHub = headers.get(HEADER_LINK);
        }
        if (linkFromGitHub != null) {
            String[] linksArr = linkFromGitHub.split(",");
            for (String string : linksArr) {
                if (string.contains(NEXT_LINK)) {
                    return string.split(";")[0].replace("<", "").replace(">", "");
                }
            }
        }
        return null;
    }

    @Override
    public String getNextLink() {
        return nextLink;
    }
}
