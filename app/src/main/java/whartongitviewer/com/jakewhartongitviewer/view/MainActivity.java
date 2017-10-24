package whartongitviewer.com.jakewhartongitviewer.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import java.lang.ref.WeakReference;

import whartongitviewer.com.jakewhartongitviewer.presenter.Presenter;
import whartongitviewer.com.jakewhartongitviewer.R;
import whartongitviewer.com.jakewhartongitviewer.presenter.IRepoPresenter;

public class MainActivity extends AppCompatActivity implements IRepoListView {

    IRepoPresenter presenter;
    RecyclerView recyclerViewRepos;
    ReposRecAdapter recAdapter;
    ProgressBar progressBar;
    FloatingActionButton fbutton;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.progress_main_activity);
        presenter = Presenter.getInstance();
        recyclerViewRepos = (RecyclerView) findViewById(R.id.rec_view_main_activity);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewRepos.setLayoutManager(layoutManager);

        fbutton = (FloatingActionButton) findViewById(R.id.fab);
        fbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgress(true);
                fbutton.setVisibility(View.GONE);
                presenter.setIfFloatButtonVisible(false);
                presenter.getRepoList();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setRepoListView(new WeakReference<IRepoListView>(this));

        //// if rotation scren, update data from presenter
        recAdapter = presenter.getRepoRecAdapter();
        recyclerViewRepos.setAdapter(recAdapter);

        showProgress(presenter.getIfShowProgress());
        if (presenter.getIfFloatButtonVisible()) {
            fbutton.setVisibility(View.VISIBLE);
        } else {
            fbutton.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.setRepoListView(null);
    }


    @Override
    public void showDetailView(long repoId) {
        Intent intent = new Intent(this, ActivityDetail.class);
        intent.putExtra(Presenter.REPO_DETAIL_ID, repoId);
        startActivity(intent);
    }

    @Override
    public void showProgress(final boolean isShowProgress) {
        presenter.setIfShowProgress(isShowProgress);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isShowProgress) {
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void showErrorMessage(final String message) {
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
                if(alertDialog==null || !alertDialog.isShowing()) {
                    alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        });
    }

}
