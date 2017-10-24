package whartongitviewer.com.jakewhartongitviewer.presenter;

import java.lang.ref.WeakReference;
import java.util.List;

import whartongitviewer.com.jakewhartongitviewer.model.RepoModel;
import whartongitviewer.com.jakewhartongitviewer.model.pojo.Reposit;
import whartongitviewer.com.jakewhartongitviewer.view.IRepoListView;
import whartongitviewer.com.jakewhartongitviewer.model.IRepoModel;
import whartongitviewer.com.jakewhartongitviewer.view.ReposRecAdapter;


public class Presenter implements IRepoPresenter {
    private static Presenter presenter;
    public static String REPO_DETAIL_ID = "repoId";
    private IRepoModel repoModel = new RepoModel(this);
    private WeakReference<IRepoListView> repoListViewWeakReference;
    private boolean isFloatButtonVisible;
    private boolean isShowProgress;
    private ReposRecAdapter reposRecAdapter;

    private Presenter() {
    }

    public static Presenter getInstance() {
        if (presenter == null) {
            presenter = new Presenter();
        }
        return presenter;
    }

    @Override
    public ReposRecAdapter getRepoRecAdapter() {
        if (reposRecAdapter == null) {
            repoListViewWeakReference.get().showProgress(true);
            reposRecAdapter = new ReposRecAdapter(getRepoList());
        }
        return reposRecAdapter;
    }


    @Override
    public void clickToRepoItem(long repoId) {
        repoListViewWeakReference.get().showDetailView(repoId);
    }

    @Override
    public void setRepoListView(WeakReference<IRepoListView> repoListViewWeakReference) {
        this.repoListViewWeakReference = repoListViewWeakReference;
    }

    @Override
    public List<Reposit> getRepoList() {
        return repoModel.getRepoList();
    }

    @Override
    public void getRepoListCallback(List<Reposit> repositoryList) {
        repoModel.setRepoList(repositoryList);
        if (repoListViewWeakReference != null && repoListViewWeakReference.get() != null) {
            if (reposRecAdapter != null) {
                reposRecAdapter.updateData(null);
            }
            repoListViewWeakReference.get().showProgress(false);
        }else {
            isShowProgress = false;
            isFloatButtonVisible = false;
        }
    }

    @Override
    public void getRepoListCallback(String errorMessage) {
        if (repoListViewWeakReference != null && repoListViewWeakReference.get() != null) {
            isFloatButtonVisible = true;
            repoListViewWeakReference.get().showProgress(false);
            repoListViewWeakReference.get().showErrorMessage(errorMessage);
        }
    }

    @Override
    public Reposit getRepoFromId(long currentRepoId) {
        return repoModel.getRepoFromId(currentRepoId);
    }

    @Override
    public void setIfFloatButtonVisible(boolean isVisible) {
        this.isFloatButtonVisible = isVisible;
    }

    @Override
    public boolean getIfFloatButtonVisible() {
        return isFloatButtonVisible;
    }

    @Override
    public boolean getIfShowProgress() {
        return isShowProgress;
    }

    @Override
    public void setIfShowProgress(boolean isShow) {
        this.isShowProgress = isShow;
    }
}
