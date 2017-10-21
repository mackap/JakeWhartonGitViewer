package whartongitviewer.com.jakewhartongitviewer.model;


import java.util.List;

import whartongitviewer.com.jakewhartongitviewer.GetRepoListOkHttp;
import whartongitviewer.com.jakewhartongitviewer.Net;
import whartongitviewer.com.jakewhartongitviewer.model.pojo.Reposit;
import whartongitviewer.com.jakewhartongitviewer.mvp.IRepoModel;
import whartongitviewer.com.jakewhartongitviewer.mvp.IRepoPresenter;

public class RepoModel implements IRepoModel {
    List<Reposit> repoList;
    IRepoPresenter repoPresenter;

    public RepoModel(IRepoPresenter repoPresenter) {
        this.repoPresenter = repoPresenter;
    }

    @Override
    public List<Reposit> getRepoList() {
        if (repoList!=null) {
            return repoList;
        } else {

            new GetRepoListOkHttp(Net.getOkHttpClient());
            return null;
        }
    }

    @Override
    public void setRepoList(List<Reposit> repoList) {
        this.repoList = repoList;
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
}
