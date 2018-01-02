package kulkarni.aditya.retrofittry;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RepoAdapter repoAdapter;
    ArrayList<GitHubRepo> gitHubRepos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.repo_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadData();
        repoAdapter = new RepoAdapter(this, gitHubRepos);
        recyclerView.setAdapter(repoAdapter);
        String API_BASE_URL = "https://api.github.com/";

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(API_BASE_URL)
                        .addConverterFactory(
                                GsonConverterFactory.create()
                        );

        Retrofit retrofit =
                builder
                        .client(
                                httpClient.build()
                        )
                        .build();

        // Create a very simple REST adapter which points the GitHub API endpoint.
        GitHubClient client = retrofit.create(GitHubClient.class);

        // Fetch a list of the Github repositories.
        Call<ArrayList<GitHubRepo>> call =
                client.reposForUser("adikul30");

        // Execute the call asynchronously. Get a positive or negative callback.
        call.enqueue(new Callback<ArrayList<GitHubRepo>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<GitHubRepo>> call, @NonNull Response<ArrayList<GitHubRepo>> response) {
                // The network call was a success and we got a response

                add(response.body());
//                repoAdapter.setList(response.body());
                Log.i("Response", response.body().toString());

            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<GitHubRepo>> call, @NonNull Throwable t) {
                // the network call was a failure
            }
        });

    }

    public void loadData() {
        gitHubRepos.clear();
        Realm.init(this);
        Realm realm = Realm.getDefaultInstance();
        RealmResults<GitHubRepo> query = realm.where(GitHubRepo.class).findAll();
        for (GitHubRepo repo : query) {
            if (repo.isValid()) {
                gitHubRepos.add(repo);
            }
        }
    }

    public void add(ArrayList<GitHubRepo> arrayList) {
        Realm realm = Realm.getDefaultInstance();

        for (int i = 0; i < arrayList.size(); i++) {
            realm.beginTransaction();
            GitHubRepo model = realm.createObject(GitHubRepo.class);
            model.setId(arrayList.get(i).getId());
            model.setName(arrayList.get(i).getName());
            realm.commitTransaction();
            gitHubRepos.add(model);
        }

        repoAdapter.notifyDataSetChanged();
        loadData();
    }
}
