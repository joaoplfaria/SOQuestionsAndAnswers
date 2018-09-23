package com.desenvolvimento.at.soquestionsandanswers.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.desenvolvimento.at.soquestionsandanswers.R;
import com.desenvolvimento.at.soquestionsandanswers.domain.StackOverflowRepo;
import com.desenvolvimento.at.soquestionsandanswers.interfaces.IStackOverflowAPI;

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

        retrofit = RetrofitManager.getInstance();
        iStackOverflowAPI = retrofit.create(IStackOverflowAPI.class);

    }


    public void getData(View view) {
        EditText editText = findViewById(R.id.inputUrl);

        rawCall = iStackOverflowAPI.getNoTreatment(editText.getText().toString());

        call = iStackOverflowAPI.getQuestionsAndAnswers(editText.getText().toString());

        call.enqueue(new Callback<List<StackOverflowRepo>>() {
            @Override
            public void onResponse(Call<List<StackOverflowRepo>> call, Response<List<StackOverflowRepo>> response) {
                String repos = "";

                for ( StackOverflowRepo repository: response.body()) {
                    repos += String.format("%boolean\n%string\n%string\n%string",
                            repository.getIs_answered(),
                            repository.getTitle(),
                            repository.getLink(),
                            repository.getTags()
                    );
                }

                TextView textView = findViewById(R.id.cool_response);
                textView.setText(repos);
            }

            @Override
            public void onFailure(Call<List<StackOverflowRepo>> call, Throwable t) {

            }
        });



        rawCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                TextView textView = findViewById(R.id.raw_response);
                try {
                    textView.setText(response.body().string());
                } catch (IOException exception){
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
