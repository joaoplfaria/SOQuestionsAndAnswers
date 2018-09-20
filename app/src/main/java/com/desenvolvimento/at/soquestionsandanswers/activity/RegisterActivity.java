package com.desenvolvimento.at.soquestionsandanswers.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.desenvolvimento.at.soquestionsandanswers.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtName, edtEmail, edtPassword, edtConfirmPassword, edtCPF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void saveUser(View view) {

        //LEMBRAR DE MANDAR O CARA PARA 'MAIN' LISTA COM PERGUNTAS E RESPOSTAS
    }

    public void clearForm(View view) {
    }
}
