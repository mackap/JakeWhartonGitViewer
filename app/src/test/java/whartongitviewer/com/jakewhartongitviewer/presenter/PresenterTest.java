package whartongitviewer.com.jakewhartongitviewer.presenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.lang.ref.WeakReference;

import okhttp3.Headers;
import whartongitviewer.com.jakewhartongitviewer.model.IRepoModel;
import whartongitviewer.com.jakewhartongitviewer.view.IRepoListView;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


public class PresenterTest {

    Presenter presenter;
    @Mock
    private IRepoModel repoModel;
    @Mock
    private IRepoListView repoListView;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = Presenter.getInstance();
        presenter.setReposView(new WeakReference<IRepoListView>(repoListView));
    }

    @Test
    public void getRepoListTest(){
        presenter.clickToLoadRepoButton();
       verify(repoListView).showProgress(true);
        verify(repoListView).showRepos();

    }

    @Test
    public void clickToRepoItemTest(){
        long repoId = Mockito.anyLong();
        presenter.clickToRepoItem(repoId);
        verify(repoListView).showDetailView(repoId);
    }

    @Test
    public void showErrorMessageTest(){
        presenter.setCurrentErrorMessage(Mockito.anyString());
        presenter.showErrorMessage();
        verify(repoListView).showProgress(false);
        verify(repoListView).showErrorMessage();
    }
    @Test
    public void showErrorMessageTestWitoutMessage(){
        presenter.setCurrentErrorMessage(null);
        presenter.showErrorMessage();
        verify(repoListView).showProgress(false);
        verify(repoListView, never()).showErrorMessage();
    }

    @Test
    public void getNextLinkFromGitHubCheckNullHeaders() throws Exception {
        Headers headers = null;
        String expected = null;
        assertEquals(presenter.getNextLinkFromGitHub(headers), expected);
    }

    @Test
    public void getNextLinkFromGitHubCheckIncorrectHeaders() throws Exception {
        Headers headers = new Headers.Builder().build();
        String expected = null;
        assertEquals(presenter.getNextLinkFromGitHub(headers), expected);
    }

    @Test
    public void getNextLinkFromGitHubCheckCorrectHeader() throws Exception {
        Headers headers = new Headers.Builder().add("Link", "<url>; next")
                .build();
        String expected = "url";
        assertEquals(presenter.getNextLinkFromGitHub(headers), expected);
    }
}