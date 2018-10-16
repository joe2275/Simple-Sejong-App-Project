package com.example.whgml.sejongapps.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.whgml.sejongapps.Helper.InputValidation;
import com.example.whgml.sejongapps.Model.User;
import com.example.whgml.sejongapps.R;
import com.example.whgml.sejongapps.RegisterRequest;
import com.example.whgml.sejongapps.sql.DatabaseHelper;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private final AppCompatActivity activity = RegisterActivity.this;
    private NestedScrollView nestedScrollView;
    private TextInputLayout textInputLayoutName;
    private TextInputLayout txtRegEmail;
    private TextInputLayout textInputLayoutPassword;
    private TextInputLayout textInputLayoutConfirmPassword;

    private TextInputEditText txtName;
    private TextInputEditText txtEmail;
    private TextInputEditText txtPass;
    private TextInputEditText txtConf;

    private Button btnReg;
    private AppCompatTextView appCompatTextViewLoginLink;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        initialize();
    }

    private void initialize()
    {
        nestedScrollView = (NestedScrollView)findViewById(R.id.register_nestedScrollView);
        textInputLayoutName = (TextInputLayout)findViewById(R.id.registerNameLayout);
        txtRegEmail = (TextInputLayout) findViewById(R.id.registerEmailLayout);
        textInputLayoutPassword = (TextInputLayout)findViewById(R.id.registerPasswordLayout);
        textInputLayoutConfirmPassword = (TextInputLayout)findViewById(R.id.registerConfirmPasswordLayout);

        txtName = (TextInputEditText)findViewById(R.id.registerName);
        txtEmail = (TextInputEditText)findViewById(R.id.registerEmail);
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

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.registerButton_reg:
                System.out.println("clock");
                postDataToSQLite();
                break;
            case R.id.appCompatTextViewLoginLink:
                finish();
                break;
        }
    }

    private void postDataToSQLite()
    {
        if(!inputValidation.isInputEditTextFilled(txtName, textInputLayoutName, getString(R.string.error_message_name)))
        {
            return;
        }
        if(!inputValidation.isInputEditTextFilled(txtEmail, txtRegEmail, getString(R.string.error_message_email)))
        {
            return;
        }
        if(!inputValidation.isInputEditTextEmail(txtEmail, txtRegEmail, getString(R.string.error_message_email)))
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

        if(!databaseHelper.checkUser(txtEmail.getText().toString().trim()))
        {
            user.setName(txtName.getText().toString().trim());
            user.setEmail(txtEmail.getText().toString().trim());
            user.setPassword((txtPass.getText().toString().trim()));

            databaseHelper.addUser(user);

            Snackbar.make(nestedScrollView, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
            emptyInputEditText();
        }
        else
        {
            Snackbar.make(nestedScrollView, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show();
        }
    }

    private void emptyInputEditText()
    {
        txtName.setText(null);
        txtEmail.setText(null);
        txtPass.setText(null);
        txtConf.setText(null);
    }
}
