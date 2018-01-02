package kulkarni.aditya.retrofittry;

import java.util.List;

import io.realm.RealmObject;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by ADMIN on 09-11-2017.
 */

public class GitHubRepo extends RealmObject {
    private int id;
    private String name;

    public GitHubRepo() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

