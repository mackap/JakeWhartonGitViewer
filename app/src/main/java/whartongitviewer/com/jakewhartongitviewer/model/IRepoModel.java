package whartongitviewer.com.jakewhartongitviewer.model;


import java.util.List;


import whartongitviewer.com.jakewhartongitviewer.model.pojo.Reposit;
import whartongitviewer.com.jakewhartongitviewer.presenter.IRepoPresenter;

public interface IRepoModel {
    List<Reposit> getRepoList();
    Reposit getRepoFromId(long currentRepoId);
    void addRepos(List<Reposit> repositList);
    void loadRepos(IRepoPresenter.LoadRepoCallback loadReposCallback);
    String getNextLink();
}
