package com.example.whgml.sejongapps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity{

    private EditText nameEdit;
    private EditText userNameEdit;
    private EditText passwordEdit;
    private EditText ageEdit;
    private Button registerBtn;
    private Intent mainIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
    }

    private void initialize()
    {
        nameEdit = (EditText)findViewById(R.id.registerNameText);
        userNameEdit = (EditText)findViewById(R.id.registerUserNameText);
        passwordEdit = (EditText)findViewById(R.id.registerPassword);
        ageEdit = (EditText)findViewById(R.id.registerAgeText);
        registerBtn = (Button)findViewById(R.id.registerButton);
        mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = nameEdit.getText().toString();
                final String userName = userNameEdit.getText().toString();
                final String password = passwordEdit.getText().toString();
                final String age = ageEdit.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("Success");

                            if(success)
                            {
                                startActivity(mainIntent);
                            }
                            else
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("Register Failed").setNegativeButton("Retry", null).create().show();
                            }
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                };

                RegisterRequest registerRequest = new RegisterRequest(name, userName, age, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });
        
    }
}
