package whartongitviewer.com.jakewhartongitviewer.mvp;


import java.lang.ref.WeakReference;
import java.util.List;

import whartongitviewer.com.jakewhartongitviewer.model.pojo.Reposit;
import whartongitviewer.com.jakewhartongitviewer.view.ReposRecAdapter;

public interface IRepoPresenter {

    void setRepoListView(WeakReference<IRepoListView> repoListViewWeakReference);
    List<Reposit> getRepoList();
    void getRepoListCallback(List<Reposit> repoListResponses);
    void getRepoListCallback(String errorMessage);
    void clickToRepoItem(long repository);
    Reposit getRepoFromId(long currentRepoId);
    ReposRecAdapter getRepoRecAdapter();
    void setIfFloatButtonVisible(boolean isVisible);
    boolean getIfFloatButtonVisible();
    boolean getIfShowProgress();
    void setIfShowProgress(boolean isShow);
}
