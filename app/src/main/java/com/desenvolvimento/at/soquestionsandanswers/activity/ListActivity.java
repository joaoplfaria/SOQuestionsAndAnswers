package com.desenvolvimento.at.soquestionsandanswers.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.desenvolvimento.at.soquestionsandanswers.R;
import com.desenvolvimento.at.soquestionsandanswers.domain.StackOverflowRepo;
import com.desenvolvimento.at.soquestionsandanswers.interfaces.IStackOverflowAPI;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ListActivity extends AppCompatActivity implements Callback<ResponseBody> {

    Retrofit retrofit;
    IStackOverflowAPI iStackOverflowAPI;
    Call<ResponseBody> rawCall;
    Call<ListWrapper<StackOverflowRepo>> call;
    //private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        getSupportActionBar().setTitle("Lista StackOverflow");

        retrofit = RetrofitManager.getInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.signOut:
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                Intent backToLogin = new Intent(this, LoginActivity.class);
                this.startActivity(backToLogin);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void getData(View view) {
        iStackOverflowAPI = retrofit.create(IStackOverflowAPI.class);
        EditText editText = findViewById(R.id.inputUrl);
        rawCall = iStackOverflowAPI.getNoTreatment("android", editText.getText().toString());
        rawCall.enqueue(this);


        call = iStackOverflowAPI.loadQuestions("android", editText.getText().toString());
        call.enqueue(new Callback<ListWrapper<StackOverflowRepo>>() {
            @Override
            public void onResponse(Call<ListWrapper<StackOverflowRepo>> call, Response<ListWrapper<StackOverflowRepo>> response) {
                List<StackOverflowRepo> overflowRepos = response.body().items;
                String questionsData = "";
                for (StackOverflowRepo repo : overflowRepos) {
                    questionsData += String.format("\n%s\n%s\n%s\n%s\n",
                            repo.getIs_answered(), repo.getTitle(), repo.getLink(), repo.getTags());
                }

                TextView textView = findViewById(R.id.cool_response);
                textView.setText(questionsData);
            }

            @Override
            public void onFailure(Call<ListWrapper<StackOverflowRepo>> call, Throwable t) {
                Toast.makeText(ListActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        TextView textView = findViewById(R.id.raw_response);
        if (response.isSuccessful()) {
            try {
                textView.setText(response.body().string());
            } catch (IOException e) {
                textView.setText(e.getMessage());
            }
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        Toast.makeText(ListActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
    }
}
