package com.example.whgml.sejongapps.Activity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.whgml.sejongapps.Helper.InputValidation;
import com.example.whgml.sejongapps.Model.User;
import com.example.whgml.sejongapps.R;
import com.example.whgml.sejongapps.sql.DatabaseHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;

    private final AppCompatActivity activity = RegisterActivity.this;
    private NestedScrollView nestedScrollView;
    private TextInputLayout textInputLayoutName;
    private TextInputLayout txtRegEmail;
    private TextInputLayout textInputLayoutAge;
    private TextInputLayout textInputLayoutPassword;
    private TextInputLayout textInputLayoutConfirmPassword;

    private TextInputEditText txtName;
    private TextInputEditText txtEmail;
    private TextInputEditText txtAge;
    private TextInputEditText txtPass;
    private TextInputEditText txtConf;

    private Button btnReg;
    private AppCompatTextView appCompatTextViewLoginLink;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private User user;

    private HashMap<String, User> userInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        initialize();
        mAuth = FirebaseAuth.getInstance();
    }

    private void initialize()
    {
        userInfo = new HashMap<>();
        nestedScrollView = (NestedScrollView)findViewById(R.id.register_nestedScrollView);
        textInputLayoutName = (TextInputLayout)findViewById(R.id.registerNameLayout);
        txtRegEmail = (TextInputLayout) findViewById(R.id.registerEmailLayout);
        textInputLayoutAge = (TextInputLayout)findViewById(R.id.registerAgeLayout);
        textInputLayoutPassword = (TextInputLayout)findViewById(R.id.registerPasswordLayout);
        textInputLayoutConfirmPassword = (TextInputLayout)findViewById(R.id.registerConfirmPasswordLayout);

        txtName = (TextInputEditText)findViewById(R.id.registerName);
        txtEmail = (TextInputEditText)findViewById(R.id.registerEmail);
        txtAge = (TextInputEditText)findViewById(R.id.registerAge);
        txtPass = (TextInputEditText)findViewById(R.id.registerPassword);
        txtConf = (TextInputEditText)findViewById(R.id.registerConfirmPassword);

        btnReg = (Button)findViewById(R.id.registerButton_reg);
        appCompatTextViewLoginLink = (AppCompatTextView)findViewById(R.id.appCompatTextViewLoginLink);

        btnReg.setOnClickListener(this);
        appCompatTextViewLoginLink.setOnClickListener(this);

        inputValidation = new InputValidation(activity);
        databaseHelper = new DatabaseHelper(activity);
        user = new User();
    }

    private void updateUI(FirebaseUser _user) {
        if(_user != null) {
            userInfo.clear();
            String uid = _user.getUid();
            DatabaseReference dRef = FirebaseDatabase.getInstance().getReference("info");
            userInfo.put(_user.getUid(), user);
            dRef.setValue(userInfo);
            emptyInputEditText();
        }
    }

    private void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "createUserWithEmail:success");
                            Toast.makeText(RegisterActivity.this, "Authentication Success.", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            FirebaseAuthException e = (FirebaseAuthException )task.getException();
                            Toast.makeText(RegisterActivity.this, "Authentication failed : " + e,
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.registerButton_reg:
                postDataToFirebase();
                break;
            case R.id.appCompatTextViewLoginLink:
                finish();
                break;
        }
    }

    private void postDataToFirebase()
    {
        if(!inputValidation.isInputEditTextFilled(txtName, textInputLayoutName, getString(R.string.error_message_name)))
        {
            return;
        }
        if(!inputValidation.isInputEditTextFilled(txtEmail, txtRegEmail, getString(R.string.error_message_email)))
        {
            return;
        }
        if(!inputValidation.isInputEditInteger(txtAge, textInputLayoutAge, getString(R.string.error_message_age)))
        {
            return;
        }
        if(!inputValidation.isInputEditTextFilled(txtPass, textInputLayoutPassword, getString(R.string.error_message_password)))
        {
            return;
        }
        if(!inputValidation.isInputEditTextMatches(txtPass, txtConf, textInputLayoutConfirmPassword,
                getString(R.string.error_password_match)))
        {
            return;
        }
        user = new User();
        user.setEmail(txtEmail.getText().toString());
        user.setName(txtName.getText().toString());
        user.setAge(txtAge.getText().toString());

        createAccount(user.getEmail(), txtPass.getText().toString());
    }

    private void emptyInputEditText()
    {
        txtName.setText(null);
        txtEmail.setText(null);
        txtPass.setText(null);
        txtConf.setText(null);
    }
}
