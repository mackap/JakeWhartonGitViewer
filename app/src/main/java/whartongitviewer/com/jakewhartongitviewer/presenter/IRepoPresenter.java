package whartongitviewer.com.jakewhartongitviewer.presenter;


import java.lang.ref.WeakReference;
import java.util.List;

import whartongitviewer.com.jakewhartongitviewer.model.pojo.Reposit;
import whartongitviewer.com.jakewhartongitviewer.view.IRepoListView;
import whartongitviewer.com.jakewhartongitviewer.view.ReposRecAdapter;

public interface IRepoPresenter {

    void setRepoListView(WeakReference<IRepoListView> repoListViewWeakReference);

    void getRepoList();

    void updateData(List<Reposit> repoListResponses);

    void showErrorMessage(String errorMessage);

    void clickToRepoItem(long repository);

    Reposit getRepoFromId(long currentRepoId);

    ReposRecAdapter getRepoRecAdapter();

    void setStates(Presenter.STATES currentStates);

    Presenter.STATES getCurrentStates();

    String getCurrentErrorMessage();

    boolean isShowNextButton();
}
