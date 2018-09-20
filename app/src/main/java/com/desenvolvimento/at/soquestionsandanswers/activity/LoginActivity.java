package com.desenvolvimento.at.soquestionsandanswers.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.desenvolvimento.at.soquestionsandanswers.DAO.UserDAO;
import com.desenvolvimento.at.soquestionsandanswers.R;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmailLogin, edtPasswordLogin;
    private UserDAO userDAO = new UserDAO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Login");

        //Views
        edtEmailLogin = findViewById(R.id.edtEmailLogin);
        edtPasswordLogin = findViewById(R.id.edtPasswordLogin);

    }

    public void loginUser(View view) {

        userDAO.onAuthSignContact(
                edtEmailLogin.getText().toString(),
                edtPasswordLogin.getText().toString());

    }

    public void registerUser(View view) {
        Intent toRegister = new Intent(this, RegisterActivity.class);
        startActivity(toRegister);
    }
}
