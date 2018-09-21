package com.desenvolvimento.at.soquestionsandanswers.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import com.desenvolvimento.at.soquestionsandanswers.DAO.UserDAO;
import com.desenvolvimento.at.soquestionsandanswers.R;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmailLogin, edtPasswordLogin;
    private UserDAO userDAO = new UserDAO();
    private boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Login");

        //Views
        edtEmailLogin = findViewById(R.id.edtEmailLogin);
        edtPasswordLogin = findViewById(R.id.edtPasswordLogin);
    }

    //Validação exclusiva para e-mail
    private boolean isEmail(EditText text) {
        CharSequence chrEmail = text.getText().toString();
        return (!TextUtils.isEmpty(chrEmail) && Patterns.EMAIL_ADDRESS.matcher(chrEmail).matches());
    }

    public void validadeLoginForm() {
        flag = false;

        if (!isEmail(edtEmailLogin) && edtEmailLogin.getText().toString().isEmpty()) {
            edtEmailLogin.setError("Digite seu e-mail corretamente para fazer o login");
            flag = true;
        }
        if (edtPasswordLogin.getText().toString().isEmpty()) {
            edtPasswordLogin.setError("Digite sua senha para fazer o login");
            flag = true;
        }
    }

    public void loginUser(View view) {
        validadeLoginForm();
        if (!flag) {
            userDAO.onAuthSignContact(
                    edtEmailLogin.getText().toString(),
                    edtPasswordLogin.getText().toString());

            //Intent toList = new Intent(this, listavaiaqui);
            //startActivity(toList);
        }

    }

    public void registerUser(View view) {
        Intent toRegister = new Intent(this, RegisterActivity.class);
        startActivity(toRegister);
    }
}
