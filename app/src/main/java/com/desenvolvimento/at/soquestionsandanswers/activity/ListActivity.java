package com.desenvolvimento.at.soquestionsandanswers.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

    private TextInputEditText inputEditText;
    private TextView txtCoolResponse, txtRawResponse;
    Retrofit retrofit;
    IStackOverflowAPI iStackOverflowAPI;
    Call<ResponseBody> rawCall;
    Call<List<StackOverflowRepo>> SORepo;
    //private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //Views
        inputEditText = findViewById(R.id.inputUrl);
        txtCoolResponse = findViewById(R.id.cool_response);
        txtRawResponse = findViewById(R.id.raw_response);

        //Instaces
        retrofit = RetrofitManager.getInstance();
        iStackOverflowAPI = retrofit.create(IStackOverflowAPI.class);
        //progressbar vai aqui
    }


    public void getData(View view) {
        rawCall = iStackOverflowAPI.getQuestionsAndAnswers(inputEditText.getText().toString());

        //SORepo = iStackOverflowAPI.getNoTreatment(inputEditText.getText().toString());

        /*SORepo.enqueue(new Callback<List<StackOverflowRepo>>() {
            @Override
            public void onResponse(Call<List<StackOverflowRepo>> call, Response<List<StackOverflowRepo>> response) {
                String repos = "";

                for (StackOverflowRepo repository : response.body()) {
                    repos += String.format("%s\n%s\n%s\n%s\n",
                            repository.getTitle(),
                            repository.getIs_answered(),
                            repository.getTags(),
                            repository.getLink());
                }

                txtCoolResponse.setText(repos);
            }

            @Override
            public void onFailure(Call<List<StackOverflowRepo>> call, Throwable t) {
                Toast.makeText(ListActivity.this, "Falha ao carregar os dados da API", Toast.LENGTH_SHORT).show();
            }
        });*/

        rawCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    txtRawResponse.setText(response.body().string());
                } catch (IOException e) {
                    Toast.makeText(ListActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ListActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
