package whartongitviewer.com.jakewhartongitviewer.presenter;

import java.lang.ref.WeakReference;
import java.util.List;

import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import whartongitviewer.com.jakewhartongitviewer.model.RepoModel;
import whartongitviewer.com.jakewhartongitviewer.model.net.RetrofitInstance;
import whartongitviewer.com.jakewhartongitviewer.model.net.NetworkAPI;
import whartongitviewer.com.jakewhartongitviewer.model.pojo.Reposit;
import whartongitviewer.com.jakewhartongitviewer.view.IRepoListView;
import whartongitviewer.com.jakewhartongitviewer.model.IRepoModel;
import whartongitviewer.com.jakewhartongitviewer.view.ReposRecAdapter;


public class Presenter implements IRepoPresenter {
    private static final Presenter presenter = new Presenter();

    public static enum STATES {PROGRESS, SHOW_REPOS, SHOW_ERROR}

    private STATES currentStates;

    private final String HEADER_LINK = "Link";
    private final String NEXT_LINK = "next";
    String nextLink;
    private final String USER = "JakeWharton";

    public static String REPO_DETAIL_ID = "repoId";
    private NetworkAPI.GitHubService api;

    String currentErrorMessage;
    private IRepoModel repoModel;
    private WeakReference<IRepoListView> repoListViewWeakReference;
    private ReposRecAdapter reposRecAdapter;

    private Presenter() {
        repoModel = new RepoModel(this);
        api = RetrofitInstance.getRetrofit().create(NetworkAPI.GitHubService.class);
    }

    public static Presenter getInstance() {
        return presenter;
    }

    @Override
    public ReposRecAdapter getRepoRecAdapter() {
        if (reposRecAdapter == null) {
            reposRecAdapter = new ReposRecAdapter(repoModel.getRepoList());
        }
        return reposRecAdapter;
    }

    @Override
    public void getRepoList() {
        if (repoListViewWeakReference != null && repoListViewWeakReference.get() != null) {
            repoListViewWeakReference.get().showProgress(true);
        }

        currentStates = STATES.PROGRESS;

        Call<List<Reposit>> repoListCall;

        //// check if need load next page data
        if (nextLink == null) {
            repoListCall = api.getRepos(USER);
        } else {
            repoListCall = api.getNextUserRepo(nextLink);
        }

        loadRepo(repoListCall);
    }

    private void loadRepo(Call<List<Reposit>> repoListCall) {

        repoListCall.enqueue(new Callback<List<Reposit>>() {
            @Override
            public void onResponse(Call<List<Reposit>> call, Response<List<Reposit>> response) {
                nextLink = getNextLinkFromGitHub(response.headers());
                repoModel.addRepos(response.body());
                currentStates = STATES.SHOW_REPOS;
                if (repoListViewWeakReference != null && repoListViewWeakReference.get() != null) {
                    repoListViewWeakReference.get().showRepos();
                }
            }

            @Override
            public void onFailure(Call<List<Reposit>> call, Throwable t) {
                currentErrorMessage = t.getMessage();
                showErrorMessage();
            }
        });
    }

    @Override
    public void clickToRepoItem(long repoId) {
        repoListViewWeakReference.get().showDetailView(repoId);
    }

    @Override
    public void setRepoListView(WeakReference<IRepoListView> repoListViewWeakReference) {
        this.repoListViewWeakReference = repoListViewWeakReference;
    }

    /**
     * show  error message, which save in currentErrorMessage
     */
    @Override
    public void showErrorMessage() {

        currentStates = STATES.SHOW_ERROR;
        if (repoListViewWeakReference != null && repoListViewWeakReference.get() != null) {
            repoListViewWeakReference.get().showProgress(false);
            if (currentErrorMessage != null) {
                repoListViewWeakReference.get().showErrorMessage();
            }
        }
    }

    /**
     * @param currentRepoId for details view
     * @return Reposit object with currentRepoId
     */
    @Override
    public Reposit getRepoFromId(long currentRepoId) {
        return repoModel.getRepoFromId(currentRepoId);
    }

    /**
     * check if show button to load next page data
     */
    @Override
    public boolean isShowNextButton() {
        return nextLink != null;
    }

    /**
     * @param headers from responce
     * @return url next page with data if it is exist
     */
    public String getNextLinkFromGitHub(Headers headers) {
        String linkFromGitHub = null;
        if(headers!=null) {
            linkFromGitHub   = headers.get(HEADER_LINK);
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
    public void setStates(STATES currentStates) {
        this.currentStates = currentStates;
    }

    @Override
    public String getCurrentErrorMessage() {
        return currentErrorMessage;
    }

    @Override
    public STATES getCurrentStates() {
        return currentStates;
    }
}
