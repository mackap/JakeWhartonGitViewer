package whartongitviewer.com.jakewhartongitviewer.mvp;


import java.util.List;

import whartongitviewer.com.jakewhartongitviewer.model.pojo.Reposit;

public interface IRepoListView {
    void showProgress(boolean isShowProgress);
    void showDetailView(long repoId);
    void showErrorMessage(String message);
}
