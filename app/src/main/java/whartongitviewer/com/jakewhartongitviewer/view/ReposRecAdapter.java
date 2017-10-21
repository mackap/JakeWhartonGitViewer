package whartongitviewer.com.jakewhartongitviewer.view;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import whartongitviewer.com.jakewhartongitviewer.R;
import whartongitviewer.com.jakewhartongitviewer.presenter.Presenter;
import whartongitviewer.com.jakewhartongitviewer.model.pojo.Reposit;


public class ReposRecAdapter extends RecyclerView.Adapter<ReposRecAdapter.RepHolder> {
    private final String TAG = getClass().getSimpleName();
    private List<Reposit> repositoryList;
    private Context context;

    public ReposRecAdapter(List<Reposit> repositoryList) {
        this.repositoryList = repositoryList;
    }

    @Override
    public RepHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.repo_item, null, false);
        this.context = parent.getContext();
        return new RepHolder(view);
    }

    @Override
    public void onBindViewHolder(RepHolder holder, int position) {

        if (repositoryList != null) {
            final Reposit repository = repositoryList.get(position);
            if (repository != null) {
                holder.tvRepTitle.setText(repository.getName());
                holder.tvRepShortDescr.setText(repository.getDescription());
                holder.tvLang.setText(repository.getLanguage() != null ? repository.getLanguage().toString() : "---");
                holder.tvStarsCount.setText(context.getString(R.string.text_stars)
                        + repository.getStargazersCount());

                holder.tvForksCount.setText(context.getString(R.string.text_forks)
                        + repository.getForksCount() + "");

                holder.tvUpdateDate.setText(context.getString(R.string.text_update_on)
                        + getFormatData(repository.getUpdatedAt()));
                holder.relLayoutRepoItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Presenter.getInstance().clickToRepoItem(repository.getId());
                    }
                });
            }
        }
    }

    private String getFormatData(String strDate) {
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date date;
        String out = " ";
        try {
            date = parser.parse(strDate);
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy", Locale.ENGLISH);
            out += dateFormat.format(date);
        } catch (ParseException e) {
            Log.d(TAG, e.getMessage());
        }
        return out;
    }

    @Override
    public int getItemCount() {
        return repositoryList == null ? 0 : repositoryList.size();
    }


    public void updateData(List<Reposit> repositoryList) {
        this.repositoryList = repositoryList;
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });

    }

    public static class RepHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rel_layout_repo_item)
        RelativeLayout relLayoutRepoItem;
        @BindView(R.id.tv_title_repo_item)
        TextView tvRepTitle;
        @BindView(R.id.tv_short_descr_repo_item)
        TextView tvRepShortDescr;
        @BindView(R.id.tv_lang_repo_item)
        TextView tvLang;
        @BindView(R.id.tv_stars_repo_item)
        TextView tvStarsCount;
        @BindView(R.id.tv_fork_repo_item)
        TextView tvForksCount;
        @BindView(R.id.tv_update_date_repo_item)
        TextView tvUpdateDate;

        public RepHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
