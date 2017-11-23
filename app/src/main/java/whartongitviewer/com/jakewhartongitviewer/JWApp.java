package whartongitviewer.com.jakewhartongitviewer;

import android.app.Application;

import whartongitviewer.com.jakewhartongitviewer.presenter.Presenter;


public class JWApp extends Application {
    Presenter presenter;
    @Override
    public void onCreate() {
        super.onCreate();
        presenter = Presenter.getInstance();
    }
    public Presenter getPresenter(){
        return presenter;
    }
}
