package whartongitviewer.com.jakewhartongitviewer.presenter;

import android.support.test.espresso.idling.CountingIdlingResource;

import java.lang.ref.WeakReference;

import whartongitviewer.com.jakewhartongitviewer.model.RepoModel;
import whartongitviewer.com.jakewhartongitviewer.model.pojo.Reposit;
import whartongitviewer.com.jakewhartongitviewer.util.EspressoIdlingResource;
import whartongitviewer.com.jakewhartongitviewer.view.IRepoListView;
import whartongitviewer.com.jakewhartongitviewer.model.IRepoModel;
import whartongitviewer.com.jakewhartongitviewer.view.ReposRecAdapter;

import static whartongitviewer.com.jakewhartongitviewer.presenter.Presenter.STATES.SHOW_REPOS;
import static whartongitviewer.com.jakewhartongitviewer.util.EspressoIdlingResource.increment;


public class Presenter implements IRepoPresenter {
    private static final Presenter presenter = new Presenter();

    public enum STATES {PROGRESS, SHOW_REPOS, SHOW_ERROR}
    private STATES currentStates = SHOW_REPOS;
    public static String REPO_DETAIL_ID = "repoId";
    String currentErrorMessage;
    private IRepoModel repoModel;
    private WeakReference<IRepoListView> repoListViewWeakReference;
    private ReposRecAdapter reposRecAdapter;

    private Presenter() {
        repoModel = new RepoModel(this);
    }

    public static Presenter getInstance() {
        return presenter;
    }

    @Override
    public ReposRecAdapter getRepoRecAdapter() {
        if (reposRecAdapter == null) {
            reposRecAdapter = new ReposRecAdapter(repoModel.getRepoList());
        } else {
            reposRecAdapter.updateRepoList(repoModel.getRepoList());
        }
        return reposRecAdapter;
    }

    @Override
    public void clickToLoadRepoButton() {

        repoListViewWeakReference.get().showProgress(true);
        currentStates = STATES.PROGRESS;
        EspressoIdlingResource.increment();
        repoModel.loadRepos(new LoadRepoCallback() {
            @Override
            public void onSuccess() {
                currentStates = SHOW_REPOS;
                if (repoListViewWeakReference != null && repoListViewWeakReference.get() != null) {
                    repoListViewWeakReference.get().showRepos();
                }
                EspressoIdlingResource.decrement();
            }

            @Override
            public void onError(String errorMessage) {
                currentErrorMessage = errorMessage;
                showErrorMessage();
                EspressoIdlingResource.decrement();
            }
        });

    }


    @Override
    public void clickToRepoItem(long repoId) {
        repoListViewWeakReference.get().showDetailView(repoId);
    }

    @Override
    public void setReposView(WeakReference<IRepoListView> repoListViewWeakReference) {
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
        return repoModel.getNextLink() != null || repoModel.getRepoList() == null;
    }


    @Override
    public void setStates(STATES currentStates) {
        this.currentStates = currentStates;
    }

    @Override
    public void setCurrentErrorMessage(String message) {
        this.currentErrorMessage = message;
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
