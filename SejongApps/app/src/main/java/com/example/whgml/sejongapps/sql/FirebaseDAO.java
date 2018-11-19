package com.example.whgml.sejongapps.sql;

import android.app.Activity;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.widget.Toast;

import com.example.whgml.sejongapps.Activity.LoginActivity;
import com.example.whgml.sejongapps.Model.User;
import com.example.whgml.sejongapps.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.Executor;

public class FirebaseDAO {
    private Activity activity;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private User user;

    public FirebaseDAO(Activity activity) {
        mAuth = FirebaseAuth.getInstance();
        this.activity = activity;
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
    }

    public User getCurrentUser() {
        FirebaseUser _user = FirebaseAuth.getInstance().getCurrentUser();
        if (_user != null) {
            // Name, email address, and profile photo Url
            user = new User();
            user.setEmail(_user.getEmail());
        }
        else {
            user = null;
        }

        return user;
    }

    public User signIn(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser _user = mAuth.getCurrentUser();
                            user = new User();
                            user.setEmail(_user.getEmail());
                        } else {
                            user = null;
                        }
                    }
                });

        return user;
    }

    public User createAccount(String email, String password, String name ,String age) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser _user = mAuth.getCurrentUser();
                            user = new User();
                            user.setEmail(_user.getEmail());
                        } else {
                            user = null;
                        }
                        // ...
                    }
                });
//        if(user != null) {
//            user.setName(name);
//            user.setAge(age);
//            myRef.child("students").child(user.getEmail()).setValue(user);
//        }

        return user;
    }
}
