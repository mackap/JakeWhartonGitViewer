package whartongitviewer.com.jakewhartongitviewer.view;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import whartongitviewer.com.jakewhartongitviewer.JWApp;
import whartongitviewer.com.jakewhartongitviewer.R;
import whartongitviewer.com.jakewhartongitviewer.model.pojo.Reposit;
import whartongitviewer.com.jakewhartongitviewer.presenter.Presenter;


public class ActivityDetail extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    @BindView(R.id.tv_name_act_detail)
    TextView tvRepName;
    @BindView(R.id.tv_descr_act_detail)
    TextView tvRepDescr;
    @BindView(R.id.tv_watches_act_detail)
    TextView tvWatchers;
    @BindView(R.id.tv_stars_act_detail)
    TextView tvStars;
    @BindView(R.id.tv_fork_act_detail)
    TextView tvForks;
    @BindView(R.id.tv_issues_act_detail)
    TextView tvIssues;
    Presenter presenter;
    Reposit repository;
    long currentRepoId;
    Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        presenter = ((JWApp) getApplication()).getPresenter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        unbinder = ButterKnife.bind(this);
        ////// in this place we solve rotation screen  - intent the same always
        currentRepoId = getIntent().getLongExtra(Presenter.REPO_DETAIL_ID, 0);
        repository = presenter.getRepoFromId(currentRepoId);
        if (repository != null) {
            tvRepName.setText(repository.getName());
            tvRepDescr.setText(repository.getDescription());
            tvForks.setText(getString(R.string.text_forks)+repository.getForksCount());
            tvStars.setText(getString(R.string.text_stars)+repository.getStargazersCount());
            tvIssues.setText(getString(R.string.text_issues)+repository.getOpenIssuesCount());
            tvWatchers.setText(getString(R.string.text_watch)+repository.getOpenIssuesCount());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbinder.unbind();
    }
}
