package whartongitviewer.com.jakewhartongitviewer.presenter;

import android.app.Application;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;

import okhttp3.Headers;
import whartongitviewer.com.jakewhartongitviewer.JWApp;

import static org.junit.Assert.*;


public class PresenterTest {

    Presenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = Presenter.getInstance();

    }

    @Test
    public void getNextLinkFromGitHubCheckNullHeaders() throws Exception {
        Headers headers =null;
        String expected = null;
        assertEquals(presenter.getNextLinkFromGitHub(headers),expected);
    }

    @Test
    public void getNextLinkFromGitHubCheckIncorrectHeaders() throws Exception {
        Headers headers =new Headers.Builder().build();
        String expected = null;
        assertEquals(presenter.getNextLinkFromGitHub(headers), expected);
    }

    @Test
    public void getNextLinkFromGitHubCheckCorrectHeader() throws Exception {
        Headers headers =new Headers.Builder().add("Link", "<url>; next")
                .build();
        String expected = "url";
        assertEquals(presenter.getNextLinkFromGitHub(headers), expected);
    }
}