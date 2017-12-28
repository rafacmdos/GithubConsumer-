package br.com.githubprofile.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.List;
import javax.inject.Inject;
import br.com.githubprofile.R;
import br.com.githubprofile.adapter.RepoAdapter;
import br.com.githubprofile.app.GithubApplication;
import br.com.githubprofile.callback.GetRepositoriesCallback;
import br.com.githubprofile.component.GithubComponent;
import br.com.githubprofile.models.Repo;
import br.com.githubprofile.services.GitHubService;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
/**
 * Created by Priscylla-SSD-2016 on 27/12/2017.
 * Exibe detalhes do usário do github: nome, foto e repositórios.
 */

public class UserDetailsActivity extends AppCompatActivity {

    @BindView(R.id.iv_avatar)
    ImageView iv_avatar;
    @BindView(R.id.tv_username)
    TextView tv_username;
    @BindView(R.id.lv_repo)
    ListView lv_repo;
    Bundle bundle;

    @Inject
    GitHubService gitHubService;
    private GithubComponent component;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        ButterKnife.bind(this);
        GithubApplication application = (GithubApplication) getApplication();
        component = application.getComponent();
        component.inject(this);

        //recupera dados do usuário pelo getExtras
        bundle = getIntent().getExtras();
        String avatar = bundle.getString("avatar_url");
        String login = bundle.getString("login");

        if(!(avatar.equals(null)) && !(avatar.isEmpty()) && !(login.equals(null)) && !(login.isEmpty())){
            Picasso.with(this)
                    .load(avatar)
                    .into(iv_avatar);
            tv_username.setText(login);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl( "https://api.github.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            gitHubService = retrofit.create(GitHubService.class);
            getRepos(login);

        }

    }

    public void getRepos(String login){
        Call<List<Repo>> call = gitHubService.listRepos(login);
        call.enqueue(new GetRepositoriesCallback(this));
    }

    public void populateReposAdapter(List<Repo> repos){
        RepoAdapter repoAdapter = new RepoAdapter(repos,this);
        lv_repo.setAdapter(repoAdapter);
    }


}
