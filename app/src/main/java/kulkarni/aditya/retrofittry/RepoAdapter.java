package kulkarni.aditya.retrofittry;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by adicool on 12/11/17.
 */

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.ViewHolder> {

    private Context context;
    private ArrayList<GitHubRepo> gitHubRepoArrayList;

    public RepoAdapter(Context context, ArrayList<GitHubRepo> gitHubRepoArrayList) {
        this.context = context;
        this.gitHubRepoArrayList = gitHubRepoArrayList;
    }

    public void setList(ArrayList<GitHubRepo> gitHubRepos) {
        this.gitHubRepoArrayList = gitHubRepos;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.repoName.setText(gitHubRepoArrayList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        if(gitHubRepoArrayList == null){
            return 0;
        } else {
            return gitHubRepoArrayList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView repoName;

        public ViewHolder(View itemView) {
            super(itemView);

            repoName = (TextView) itemView.findViewById(R.id.repo_name);

        }
    }
}
