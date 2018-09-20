package com.desenvolvimento.at.soquestionsandanswers.DAO;

import android.support.annotation.NonNull;
import android.util.Log;

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
    private String TAG = "LOG: ";
    private boolean flagSnapshot;

    public void onAuthCreateContact(String email, String password) {

        auth = FirebaseConfig.getFirebaseAuth();

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

    public void onAuthSignContact(String email, String password) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "Usuário logado!");
                } else {
                    Log.d(TAG, "Falha ao logar o usuário!");
                }
            }
        });
    }

    public void onDatabaseRegisterContact(User user) {

        reference = FirebaseDatabase.getInstance().getReference("contatos");
        reference.keepSynced(true);

        try {
            reference = FirebaseConfig.getFirebase().child("contatos");
            //Insere no firebase (o push() cria uma chave única, um Id para o registro).
            reference.push().setValue(user);
        } catch (Exception e) {
            Log.v(TAG, e.toString());
        }
    }

}
