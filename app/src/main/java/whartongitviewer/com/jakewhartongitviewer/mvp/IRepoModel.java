package whartongitviewer.com.jakewhartongitviewer.mvp;


import java.util.List;


import whartongitviewer.com.jakewhartongitviewer.model.pojo.Reposit;

public interface IRepoModel {
    List<Reposit> getRepoList();
void setRepoList(List<Reposit> repoList);
    Reposit getRepoFromId(long currentRepoId);
}
