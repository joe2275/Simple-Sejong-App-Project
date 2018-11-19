package com.example.whgml.sejongapps.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.whgml.sejongapps.Helper.InputValidation;
import com.example.whgml.sejongapps.Model.User;
import com.example.whgml.sejongapps.R;
import com.example.whgml.sejongapps.sql.DatabaseHelper;
import com.example.whgml.sejongapps.sql.FirebaseDAO;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements Button.OnClickListener {
    private FirebaseDAO dao;

    private Button loginBtn;
    private TextView remainingText;
    private Intent userIntent;
    private Intent registerIntent;
    private StringBuilder remainingString;
    private int remainingStringLength;
    private StringBuilder outOfRemainingString;
    private int validateCounter;

    private final AppCompatActivity activity = LoginActivity.this;
    private NestedScrollView nestedScrollView;
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
        dao = new FirebaseDAO(this);
    }

    private void initialize()
    {
        validateCounter = 5;
        remainingString = new StringBuilder(getString(R.string.remainingText));
        remainingStringLength = remainingString.length();
        outOfRemainingString = new StringBuilder(getString(R.string.outOfRemaining));
        remainingText = (TextView)findViewById(R.id.remainingCount_text);


        nestedScrollView = (NestedScrollView)findViewById(R.id.login_nestedScrollView);
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

        User user = dao.signIn(txtEmail.getText().toString(), txtPass.getText().toString());
        if(user != null)
        {
            Toast.makeText(this, user.getEmail() + " Login.", Toast.LENGTH_SHORT).show();
            startActivity(userIntent);
        }
        else
        {
            Toast.makeText(this, "Login Failed.", Toast.LENGTH_SHORT).show();
        }
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
