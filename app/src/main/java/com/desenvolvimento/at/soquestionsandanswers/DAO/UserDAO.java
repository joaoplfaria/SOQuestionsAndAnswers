package com.desenvolvimento.at.soquestionsandanswers.DAO;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.desenvolvimento.at.soquestionsandanswers.activity.RegisterActivity;
import com.desenvolvimento.at.soquestionsandanswers.domain.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserDAO {

    private FirebaseAuth auth;
    private DatabaseReference reference;
    private String TAG = "LOG userDAO: ";

    public void onAuthCreateUser(String email, String password) {

        auth = FirebaseAuth.getInstance();

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "Usuário cadastrado no auth");
                } else {
                    Log.d(TAG, "Falha ao cadastrar o usuário no auth");
                }
            }
        });
    }

    public void onAuthSignUser(String email, String password, final Activity activity) {

        auth = FirebaseAuth.getInstance();

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "Usuário logado!");
                } else {
                    Toast.makeText(activity, "Usuário ou senha incorreto.", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Falha ao logar o usuário!");
                }
            }
        });
    }

    public void onDatabaseRegisterUser(User user, final RegisterActivity registerActivity) {

        reference = FirebaseDatabase.getInstance().getReference("user");
        reference.keepSynced(true);

        try {
            reference = FirebaseDatabase.getInstance().getReference().child("user");
            //Insere no firebase (o push() cria uma chave única, um Id para o registro).
            reference.push().setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(registerActivity, "Usuário cadastrado com sucesso.", Toast.LENGTH_LONG).show();
                        Log.d(TAG, "Usuário cadastrado no database");
                    } else {
                        Toast.makeText(registerActivity, "Falha ao cadastrar o usuário.", Toast.LENGTH_LONG).show();
                        Log.d(TAG, "Falha ao cadastrar usuário no database");
                    }
                }
            });
        } catch (Exception e) {
            Log.v(TAG, e.toString());
        }
    }

}
