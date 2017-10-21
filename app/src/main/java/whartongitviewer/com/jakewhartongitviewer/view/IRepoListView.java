package whartongitviewer.com.jakewhartongitviewer.view;


import java.util.List;

import whartongitviewer.com.jakewhartongitviewer.model.pojo.Reposit;

public interface IRepoListView {
    void showProgress(boolean isShowProgress);
    void showDetailView(long repoId);
    void showErrorMessage(String message);
}
