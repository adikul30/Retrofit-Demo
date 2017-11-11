package kulkarni.aditya.retrofittry;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by ADMIN on 09-11-2017.
 */

public interface GitHubClient {
    @GET("/users/{user}/repos")
    Call<ArrayList<GitHubRepo>> reposForUser(
            @Path("user") String user
    );
}
