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
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ListActivity extends AppCompatActivity {

    Retrofit retrofit;
    IStackOverflowAPI iStackOverflowAPI;
    Call<ResponseBody> rawCall;
    Call<List<StackOverflowRepo>> call;
    //private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        getSupportActionBar().setTitle("Lista StackOverflow");

        //Instancias
        retrofit = RetrofitManager.getInstance();
        iStackOverflowAPI = retrofit.create(IStackOverflowAPI.class);

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
        EditText editText = findViewById(R.id.inputUrl);

        rawCall = iStackOverflowAPI.getNoTreatment(editText.getText().toString());

        //call = iStackOverflowAPI.getQuestionsAndAnswers(editText.getText().toString());

/*
        Call<List<StackOverflowRepo>> call = new RetrofitManager().getStackOverflowRepo().getQuestionsAndAnswers(editText.getText().toString());
        call.enqueue(new Callback<List<StackOverflowRepo>>() {
            @Override
            public void onResponse(Call<List<StackOverflowRepo>> call, Response<List<StackOverflowRepo>> response) {
                List<StackOverflowRepo> stackOverflowRepo = response.body();
                final TextView textView = findViewById(R.id.cool_response);
                textView.setText(stackOverflowRepo.toString());
            }

            @Override
            public void onFailure(Call<List<StackOverflowRepo>> call, Throwable t) {
                Toast.makeText(ListActivity.this, "FALHA", Toast.LENGTH_SHORT).show();
            }
        });
*/

        /*call.enqueue(new Callback<List<StackOverflowRepo>>() {
            @Override
            public void onResponse(Call<List<StackOverflowRepo>> call, Response<List<StackOverflowRepo>> response) {
                StringBuilder repos = new StringBuilder();

                for (StackOverflowRepo repository : response.body()) {
                    repos.append(String.format("%b\n%s\n%s\n%s",
                            repository.getIs_answered(),
                            repository.getTitle(),
                            repository.getLink(),
                            repository.getTags()
                    ));
                }


            }

            @Override
            public void onFailure(Call<List<StackOverflowRepo>> call, Throwable t) {
                Toast.makeText(ListActivity.this, "FALHA", Toast.LENGTH_SHORT).show();
            }
        });*/

        rawCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                TextView textView = findViewById(R.id.raw_response);
                try {
                    textView.setText(response.body().string());
                } catch (IOException exception) {
                    Toast.makeText(ListActivity.this,
                            exception.getMessage(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(ListActivity.this,
                        t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
