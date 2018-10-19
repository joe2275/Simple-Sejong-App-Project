package com.example.whgml.sejongapps.Activity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.whgml.sejongapps.Helper.InputValidation;
import com.example.whgml.sejongapps.Model.User;
import com.example.whgml.sejongapps.R;
import com.example.whgml.sejongapps.sql.DatabaseHelper;

import org.w3c.dom.Text;

public class QueryActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = QueryActivity.this;
    private TextInputEditText queryNameEditText;
    private TextInputEditText queryEmailEditText;
    private TextInputEditText queryAgeEditText;
    private TextInputEditText selectNameEditText;
    private TextInputEditText queryPasswordEditText;
    private Button saveBtn;
    private Button updateBtn;
    private Button deleteBtn;
    private Button selectBtn;
    private Button selectAllBtn;
    DatabaseHelper sqlitedb;
    String name, email, age, search, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.query_activity);
        initialize();
    }

    private void initialize()
    {
        sqlitedb = new DatabaseHelper(activity);

        queryNameEditText = (TextInputEditText)findViewById(R.id.queryNameEditText);
        queryEmailEditText = (TextInputEditText)findViewById(R.id.queryEmailEditText);
        queryAgeEditText = (TextInputEditText)findViewById(R.id.queryAgeEditText);
        selectNameEditText = (TextInputEditText)findViewById(R.id.selectNameEditText);
        queryPasswordEditText = (TextInputEditText)findViewById(R.id.queryPasswordEditText);
        saveBtn = (Button)findViewById(R.id.saveBtn);
        updateBtn = (Button)findViewById(R.id.updateBtn);
        deleteBtn = (Button)findViewById(R.id.deleteBtn);
        selectBtn = (Button)findViewById(R.id.selectBtn);
        selectAllBtn = (Button)findViewById(R.id.selectAllBtn);

        saveBtn.setOnClickListener(this);
        updateBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
        selectBtn.setOnClickListener(this);
        selectAllBtn.setOnClickListener(this);
    }

    private boolean integerValidation(String value)
    {
        boolean isCharacter = true;

        for(int i=0; i<value.length(); i++)
        {
            char character = value.charAt(i);
            if(character < '0' || character > '9')
            {
                isCharacter = false;
            }
        }

        if(value.isEmpty() || !isCharacter)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if(id == R.id.saveBtn)
        {
            name = queryNameEditText.getText().toString().trim();
            email = queryEmailEditText.getText().toString().trim();
            age = queryAgeEditText.getText().toString().trim();
            password = queryPasswordEditText.getText().toString().trim();

            if(name.equals("") || email.equals("") || password.equals("") || integerValidation(age))
            {
                Toast.makeText(this, "Please fill up all the fields!", Toast.LENGTH_SHORT).show();
                return;
            }
            else
            {
                User user = new User();
                user.setName(name);
                user.setEmail(email);
                user.setAge(Integer.parseInt(age));
                user.setPassword(password);
                sqlitedb.addUser(user);
            }
        }
        else if(id == R.id.selectAllBtn)
        {
            sqlitedb.selectAllUser();
        }
        else if(id == R.id.selectBtn)
        {
            search = selectNameEditText.getText().toString().trim();
            if(search.equals(""))
            {
                Toast.makeText(this, "Enter Student Name", Toast.LENGTH_SHORT).show();
                return;
            }
            User user = sqlitedb.selectUserUsingName(search);
            if(user != null)
            {
                queryNameEditText.setText(user.getName());
                queryAgeEditText.setText(user.getAge());
                queryEmailEditText.setText(user.getEmail());
                queryPasswordEditText.setText(user.getPassword());
            }
        }
        else if(id == R.id.updateBtn)
        {
            search = selectNameEditText.getText().toString().trim();
            name = queryNameEditText.getText().toString().trim();
            email = queryEmailEditText.getText().toString().trim();
            age = queryAgeEditText.getText().toString().trim();
            password = queryPasswordEditText.getText().toString().trim();

            if(search.equals(""))
            {
                Toast.makeText(this, "Please Enter Student Name to Update", Toast.LENGTH_SHORT).show();
                return;
            }
            if(name.equals("") || email.equals("") || age.equals("") || password.equals(""))
            {
                Toast.makeText(this, "Please Enter New Information", Toast.LENGTH_SHORT).show();
                return;
            }
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setAge(Integer.parseInt(age));

            sqlitedb.updateUserUsingName(search, user);
        }
        else if(id == R.id.deleteBtn)
        {
            search = selectNameEditText.getText().toString().trim();
            if(search.equals(""))
            {
                Toast.makeText(this, "Enter Student Name to Delete", Toast.LENGTH_SHORT).show();
                return;
            }

        }
    }
}
