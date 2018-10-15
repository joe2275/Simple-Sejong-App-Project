package com.example.whgml.sejongapps.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.whgml.sejongapps.R;

public class MainActivity extends AppCompatActivity implements Button.OnClickListener {

    private EditText nameText;
    private EditText passwordText;
    private Button loginBtn;
    private TextView remainingText;
    private Intent successIntent;
    private StringBuilder remainingString;
    private int remainingStringLength;
    private StringBuilder outOfRemainingString;
    private int validateCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize()
    {
        validateCounter = 5;
        remainingString = new StringBuilder(getString(R.string.remainingText));
        remainingStringLength = remainingString.length();
        outOfRemainingString = new StringBuilder(getString(R.string.outOfRemaining));
        nameText = (EditText)findViewById(R.id.name_text);
        passwordText = (EditText)findViewById(R.id.password_text);
        loginBtn = (Button)findViewById(R.id.login_button);
        remainingText = (TextView)findViewById(R.id.remainingCount_text);
        successIntent = new Intent(MainActivity.this, SecondActivity.class);
        loginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.login_button)
        {
            StringBuilder name = new StringBuilder(nameText.getText());
            StringBuilder pass = new StringBuilder(passwordText.getText());

            if(validate(name, pass))
            {
                startActivity(successIntent);
            }
        }
    }

    private boolean validate(StringBuilder name, StringBuilder password)
    {
        if(name.toString().equals("Zaoxi") && password.toString().equals("2275"))
        {
            return true;
        }
        else
        {
            validateCounter--;
            if(remainingText.getVisibility() == View.INVISIBLE) {
                remainingText.setVisibility(View.VISIBLE);
            }
            if(validateCounter <= 0)
            {
                loginBtn.setEnabled(false);
                remainingText.setText(outOfRemainingString);
                return false;
            }
            remainingString.delete(remainingStringLength, remainingString.length());
            remainingString.append(" ");
            remainingString.append(validateCounter);
            remainingText.setText(remainingString);
            return false;
        }
    }
}
