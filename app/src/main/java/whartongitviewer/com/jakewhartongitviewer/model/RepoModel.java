package whartongitviewer.com.jakewhartongitviewer.model;


import java.util.List;

import whartongitviewer.com.jakewhartongitviewer.model.pojo.Reposit;
import whartongitviewer.com.jakewhartongitviewer.presenter.IRepoPresenter;

public class RepoModel implements IRepoModel {
    private final String TAG = getClass().getSimpleName();
    List<Reposit> repoList;
    IRepoPresenter repoPresenter;

    public RepoModel(IRepoPresenter repoPresenter) {
        this.repoPresenter = repoPresenter;
    }

    @Override
    public List<Reposit> getRepoList() {
        return repoList;

    }

    @Override
    public void setRepoList(List<Reposit> repoList) {
        this.repoList = repoList;
    }

    @Override
    public void addRepos(List<Reposit> addRepositList) {
        if(repoList==null) {
            repoList = addRepositList;
        }else {
            repoList.addAll(addRepositList);
        }
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
