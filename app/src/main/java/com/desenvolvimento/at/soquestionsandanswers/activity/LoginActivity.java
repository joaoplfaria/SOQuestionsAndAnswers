package com.desenvolvimento.at.soquestionsandanswers.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import com.desenvolvimento.at.soquestionsandanswers.DAO.UserDAO;
import com.desenvolvimento.at.soquestionsandanswers.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.Login;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {


    private EditText edtEmailLogin, edtPasswordLogin;
    private UserDAO userDAO = new UserDAO();
    private boolean flag;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private String TAG = "LOG LoginActivity: ";
    private TextInputLayout textInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        textInputLayout.setPasswordVisibilityToggleEnabled(true);

        //Mudar o titulo da activity
        getSupportActionBar().setTitle("Login");

        //Views
        edtEmailLogin = findViewById(R.id.edtEmailLogin);
        edtPasswordLogin = findViewById(R.id.edtPasswordLogin);

        // Callback Login Facebook
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "Usuário logado com sucesso");
                Intent btnFaceToList = new Intent(LoginActivity.this, ListActivity.class);
                startActivity(btnFaceToList);
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "Cancelado");
            }

            @Override
            public void onError(FacebookException exception) {
                Log.d(TAG, "Erro ao tentar logar o usuário: " + exception);
            }
        });
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile"));
            }
        });


        //Verificando se o usuário atual está logado no firebase auth ou pelo facebook
        if (FirebaseAuth.getInstance().getCurrentUser() != null || isLoggedIn){
            Intent toListActivity = new Intent(this, ListActivity.class);
            startActivity(toListActivity);
            finish();
        }
    }

    //Recupera o resultado do callback
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

    //Validação do formulário de login
    public void validadeLoginForm() {
        flag = false;

        if (!isEmail(edtEmailLogin) && edtEmailLogin.getText().toString().isEmpty()) {
            edtEmailLogin.setError("Digite seu e-mail corretamente para fazer o login");
            flag = true;
        }
        if (edtPasswordLogin.getText().toString().isEmpty()) {
            //textInputLayout.setPasswordVisibilityToggleEnabled(false);
            edtPasswordLogin.setError("Digite sua senha para fazer o login");
            flag = true;
        }
    }

    //Logar usuário com seus dados cadastrados pelo formulário
    public void loginUser(View view) {
        validadeLoginForm();
        if (!flag) {
            userDAO.onAuthSignUser(
                    edtEmailLogin.getText().toString(),
                    edtPasswordLogin.getText().toString(),
                    this);

            clearFormLogin();
        }

    }

    //Botão para tela de cadastro
    public void toRegisterUser(View view) {
        Intent toRegister = new Intent(this, RegisterActivity.class);
        startActivity(toRegister);

        clearFormLogin();
    }

    //Limpar o formulário
    private void clearFormLogin() {
        edtEmailLogin.getText().clear();
        edtPasswordLogin.getText().clear();
        edtEmailLogin.requestFocus();
    }
}
