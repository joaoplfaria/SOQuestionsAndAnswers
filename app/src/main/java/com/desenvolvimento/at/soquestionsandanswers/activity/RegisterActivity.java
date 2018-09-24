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
import com.desenvolvimento.at.soquestionsandanswers.domain.User;
import com.desenvolvimento.at.soquestionsandanswers.util.MaskEditUtil;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtName, edtEmail, edtPassword, edtConfirmPassword, edtCPF;
    private UserDAO userDAO = new UserDAO();
    private User user = new User();
    private TextInputLayout textInputLayout;
    private boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setTitle("Cadastro");

        //Views
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        edtCPF = findViewById(R.id.edtCPF);
        edtCPF.addTextChangedListener(MaskEditUtil.mask(edtCPF, MaskEditUtil.FORMAT_CPF));

        textInputLayout = findViewById(R.id.txtInputLayout);
    }

    //Validação exclusiva para e-mail
    private boolean isEmail(EditText text) {
        CharSequence chrEmail = text.getText().toString();
        return (!TextUtils.isEmpty(chrEmail) && Patterns.EMAIL_ADDRESS.matcher(chrEmail).matches());
    }

    //Validar formulário
    private void validateForm() {

        flag = false;

        if (edtName.getText().toString().isEmpty()) {
            edtName.setError("O campo nome não pode ficar em branco.");
            flag = true;
        }
        if (!isEmail(edtEmail) && edtEmail.getText().toString().isEmpty()) {
            edtEmail.setError("Insira um e-mail válido. (XXX@XXXX.com");
            flag = true;
        }
        if (edtPassword.getText().toString().isEmpty()) {
            textInputLayout.setPasswordVisibilityToggleEnabled(false);
            edtPassword.setError("O campo senha não pode ficar em branco.");
            flag = true;
        }
        if (edtConfirmPassword.getText().toString().isEmpty() &&
                edtConfirmPassword.getText().toString().equals(edtConfirmPassword.getText().toString())) {
            edtConfirmPassword.setError("O campo senha não pode ficar em branco e as senhas devem ser semelhantes.");
        }
        if (edtCPF.getText().toString().isEmpty()) {
            edtCPF.setError("O campo CPF não pode ficar em branco.");
            flag = true;
        }
    }

    //Cadastrar no database
    public void saveUser(View view) {
        validateForm();
        if (!flag) {
            user.setName(edtName.getText().toString());
            user.setEmail(edtEmail.getText().toString());
            user.setPassword(edtPassword.getText().toString());
            user.setConfirmPassword(edtConfirmPassword.getText().toString());
            user.setCpf(edtCPF.getText().toString());

            //Criar usuário no Auth
            userDAO.onAuthCreateUser(edtEmail.getText().toString(), edtPassword.getText().toString());

            //Criar registro no database
            userDAO.onDatabaseRegisterUser(user, RegisterActivity.this);

            clearForm(view);
            edtName.requestFocus();
        }
    }

    //Limpar o formulário
    public void clearForm(View view) {
        edtName.getText().clear();
        edtEmail.getText().clear();
        edtPassword.getText().clear();
        edtConfirmPassword.getText().clear();
        edtCPF.getText().clear();

        edtName.requestFocus();
    }
}
