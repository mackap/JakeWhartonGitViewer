package whartongitviewer.com.jakewhartongitviewer.presenter;


import java.lang.ref.WeakReference;
import java.util.List;

import whartongitviewer.com.jakewhartongitviewer.model.IRepoModel;
import whartongitviewer.com.jakewhartongitviewer.model.pojo.Reposit;
import whartongitviewer.com.jakewhartongitviewer.view.IRepoListView;
import whartongitviewer.com.jakewhartongitviewer.view.ReposRecAdapter;

public interface IRepoPresenter {

    IRepoModel getRepoModel();

    public interface LoadRepoCallback {
        void onSuccess();
        void onError(String errorMessage);
    }
    void setReposView(WeakReference<IRepoListView> repoListViewWeakReference);

    void clickToLoadRepoButton();

    void showErrorMessage();

    void clickToRepoItem(long repository);

    Reposit getRepoFromId(long currentRepoId);

     void setStates(Presenter.STATES currentStates);

    Presenter.STATES getCurrentStates();

    String getCurrentErrorMessage();
    void  setCurrentErrorMessage(String message);

    boolean isShowNextButton();
}
