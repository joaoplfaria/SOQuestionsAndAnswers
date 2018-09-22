package com.desenvolvimento.at.soquestionsandanswers.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.desenvolvimento.at.soquestionsandanswers.DAO.UserDAO;
import com.desenvolvimento.at.soquestionsandanswers.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {



    private EditText edtEmailLogin, edtPasswordLogin;
    private UserDAO userDAO = new UserDAO();
    private boolean flag;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private String TAG = "LOG LoginActivity: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Mudar o titulo da activity
        getSupportActionBar().setTitle("Login");

        callbackManager = CallbackManager.Factory.create();

        //Views
        edtEmailLogin = findViewById(R.id.edtEmailLogin);
        edtPasswordLogin = findViewById(R.id.edtPasswordLogin);



        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Toast.makeText(LoginActivity.this, "NOSSOSCU", Toast.LENGTH_SHORT).show();
                // App code
            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "TEUCU", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Toast.makeText(LoginActivity.this, "BARRIGADODIEGO", Toast.LENGTH_SHORT).show();
                exception.printStackTrace();
            }
        });


        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();


        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
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
            userDAO.onAuthSignUser(
                    edtEmailLogin.getText().toString(),
                    edtPasswordLogin.getText().toString(),
                    this);

            clearFormLogin();
            //Intent toList = new Intent(this, listavaiaqui);
            //startActivity(toList);
        }

    }

    public void registerUser(View view) {
        Intent toRegister = new Intent(this, RegisterActivity.class);
        startActivity(toRegister);

        clearFormLogin();
    }

    private void clearFormLogin() {
        edtEmailLogin.getText().clear();
        edtPasswordLogin.getText().clear();
        edtEmailLogin.requestFocus();
    }
}
