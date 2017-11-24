package whartongitviewer.com.jakewhartongitviewer.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Response;
import whartongitviewer.com.jakewhartongitviewer.JWApp;
import whartongitviewer.com.jakewhartongitviewer.presenter.Presenter;
import whartongitviewer.com.jakewhartongitviewer.R;
import whartongitviewer.com.jakewhartongitviewer.presenter.IRepoPresenter;

public class MainActivity extends AppCompatActivity implements IRepoListView {
    private final String TAG = getClass().getSimpleName();
    IRepoPresenter presenter;
    @BindView(R.id.rec_view_main_activity)
    RecyclerView recyclerView;
    @BindView(R.id.progress_main_activity)
    ProgressBar progressBar;
    @BindView(R.id.fab)
    FloatingActionButton fbutton;
    AlertDialog alertDialog;
    Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onResume() {
        super.onResume();
        unbinder = ButterKnife.bind(this);
        presenter = ((JWApp) getApplication()).getPresenter();
        presenter.setRepoListView(new WeakReference<IRepoListView>(this));
        showCurrentState();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbinder.unbind();
        presenter.setRepoListView(null);
    }

    private void showCurrentState() {
        if (presenter.getCurrentStates() != null) {
            switch (presenter.getCurrentStates()) {
                case PROGRESS:
                    showProgress(true);
                    break;
                case SHOW_REPOS:
                    showRepos();
                    break;
                case SHOW_ERROR:
                    showErrorMessage();
                    break;
            }
        } else {
            //// if didnt found status, its mean that start app or content was lost
            loadRepos();
        }
    }

    @Override
    public void showRepos() {
        showProgress(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(presenter.getRepoRecAdapter());
        showNextButton();
    }

    private void showNextButton() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (presenter.isShowNextButton()) {
                    fbutton.setVisibility(View.VISIBLE);
                    fbutton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            presenter.getRepoList();
                        }
                    });
                } else {
                    fbutton.setVisibility(View.GONE);
                }
            }
        });
    }

    public void loadRepos() {
        presenter.getRepoList();
    }

    @Override
    public void showDetailView(long repoId) {
        Intent intent = new Intent(this, ActivityDetail.class);
        intent.putExtra(Presenter.REPO_DETAIL_ID, repoId);
        startActivity(intent);
    }

    @Override
    public void showProgress(final boolean isShowProgress) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isShowProgress) {
                    progressBar.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    fbutton.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    fbutton.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void showErrorMessage() {
        String message = presenter.getCurrentErrorMessage();
        showProgress(false);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                fbutton.setVisibility(View.VISIBLE);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage(message);
                builder.setNeutralButton(getString(R.string.text_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.dismiss();
                    }
                });
                if (alertDialog == null || !alertDialog.isShowing()) {
                    alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        });
    }

}
