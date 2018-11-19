package com.example.whgml.sejongapps.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.whgml.sejongapps.Helper.InputValidation;
import com.example.whgml.sejongapps.R;
import com.example.whgml.sejongapps.sql.DatabaseHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements Button.OnClickListener {
    private FirebaseAuth mAuth;

    private Button loginBtn;
    private TextView remainingText;
    private Intent userIntent;
    private Intent registerIntent;
    private StringBuilder remainingString;
    private int remainingStringLength;
    private StringBuilder outOfRemainingString;
    private int validateCounter;

    private final AppCompatActivity activity = LoginActivity.this;
    private TextInputLayout txtRegEmail;
    private TextInputLayout textInputLayoutPassword;

    private TextInputEditText txtEmail;
    private TextInputEditText txtPass;

    private AppCompatTextView txtReg;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        initialize();
        // Get Firebase's auth instance
        mAuth = FirebaseAuth.getInstance();
    }

    private void initialize()
    {
        validateCounter = 5;
        remainingString = new StringBuilder(getString(R.string.remainingText));
        remainingStringLength = remainingString.length();
        outOfRemainingString = new StringBuilder(getString(R.string.outOfRemaining));
        remainingText = (TextView)findViewById(R.id.remainingCount_text);


        txtRegEmail = (TextInputLayout)findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = (TextInputLayout)findViewById(R.id.textInputLayoutPassword);

        txtEmail = (TextInputEditText)findViewById(R.id.email_text);
        txtPass = (TextInputEditText)findViewById(R.id.password_text);
        loginBtn = (Button)findViewById(R.id.loginButton);
        txtReg = (AppCompatTextView)findViewById(R.id.txtReg);

        userIntent = new Intent(LoginActivity.this, NavigationActivity.class);
        registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);

        loginBtn.setOnClickListener(this);
        txtReg.setOnClickListener(this);

        databaseHelper = new DatabaseHelper(activity);
        inputValidation = new InputValidation(activity);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.loginButton:
                verifyFromFirebase();
                break;
            case R.id.txtReg:
                startActivity(registerIntent);
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser user)
    {
        emptyInputEditText();
        if(user != null) {
            startActivity(userIntent);
        }
        else
        {
            countInvalidate();
        }
    }

    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithEmail:success");
                            Toast.makeText(LoginActivity.this, "Authentication Success.", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void verifyFromFirebase()
    {
        if(!inputValidation.isInputEditTextFilled(txtEmail, txtRegEmail, getString(R.string.error_message_email)))
        {
            countInvalidate();
            return;
        }
        if(!inputValidation.isInputEditTextEmail(txtEmail, txtRegEmail, getString(R.string.error_message_email)))
        {
            countInvalidate();
            return;
        }
        if(!inputValidation.isInputEditTextFilled(txtPass, textInputLayoutPassword, getString(R.string.error_message_password)))
        {
            countInvalidate();
            return;
        }

        signIn(txtEmail.getText().toString(), txtPass.getText().toString());
    }

    private void countInvalidate()
    {
        remainingText.setEnabled(true);
        validateCounter--;
        if(remainingText.getVisibility() == View.INVISIBLE) {
            remainingText.setVisibility(View.VISIBLE);
        }
        if(validateCounter <= 0)
        {
            loginBtn.setEnabled(false);
            remainingText.setText(outOfRemainingString);
            return;
        }
        remainingString.delete(remainingStringLength, remainingString.length());
        remainingString.append(" ");
        remainingString.append(validateCounter);
        remainingText.setText(remainingString);
    }

    private void emptyInputEditText()
    {
        txtEmail.setText(null);
        txtPass.setText(null);
    }
}
