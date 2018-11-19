package com.example.whgml.sejongapps.Activity;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.whgml.sejongapps.Helper.InputValidation;
import com.example.whgml.sejongapps.Model.User;
import com.example.whgml.sejongapps.R;
import com.example.whgml.sejongapps.sql.DatabaseHelper;

import org.w3c.dom.Text;

public class QueryActivity extends Fragment implements View.OnClickListener {
    private final Activity activity = this.getActivity();
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

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.query_activity);
//        initialize();
//    }
    private View myView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.query_activity, container, false);
        initialize();
        return myView;
    }

    private void initialize()
    {
        sqlitedb = new DatabaseHelper(activity);

        queryNameEditText = (TextInputEditText)myView.findViewById(R.id.queryNameEditText);
        queryEmailEditText = (TextInputEditText)myView.findViewById(R.id.queryEmailEditText);
        queryAgeEditText = (TextInputEditText)myView.findViewById(R.id.queryAgeEditText);
        selectNameEditText = (TextInputEditText)myView.findViewById(R.id.selectNameEditText);
        queryPasswordEditText = (TextInputEditText)myView.findViewById(R.id.queryPasswordEditText);
        saveBtn = (Button)myView.findViewById(R.id.saveBtn);
        updateBtn = (Button)myView.findViewById(R.id.updateBtn);
        deleteBtn = (Button)myView.findViewById(R.id.deleteBtn);
        selectBtn = (Button)myView.findViewById(R.id.selectBtn);
        selectAllBtn = (Button)myView.findViewById(R.id.selectAllBtn);

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

        if(value.isEmpty() || isCharacter)
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
                Toast.makeText(getContext(), "Please fill up all the fields!", Toast.LENGTH_SHORT).show();
                return;
            }
            else
            {
                User user = new User();
                user.setName(name);
                user.setEmail(email);
                user.setAge(age);
                user.setPassword(password);
                sqlitedb.addUser(user);
                Toast.makeText(activity, "New User Added", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getContext(), "Enter Student Name", Toast.LENGTH_SHORT).show();
                return;
            }
            User user = sqlitedb.selectUserUsingName(search);
            if(user != null)
            {
                queryNameEditText.setText(user.getName());
                queryAgeEditText.setText(String.valueOf(user.getAge()));
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
                Toast.makeText(getContext(), "Please Enter Student Name to Update", Toast.LENGTH_SHORT).show();
                return;
            }
            if(name.equals("") || email.equals("") || age.equals("") || password.equals(""))
            {
                Toast.makeText(getContext(), "Please Enter New Information", Toast.LENGTH_SHORT).show();
                return;
            }
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setAge(age);
            user.setPassword(password);
            sqlitedb.updateUserUsingName(search, user);
        }
        else if(id == R.id.deleteBtn)
        {
            search = selectNameEditText.getText().toString().trim();
            if(search.equals(""))
            {
                Toast.makeText(getContext(), "Enter Student Name to Delete", Toast.LENGTH_SHORT).show();
                return;
            }

            sqlitedb.deleteUserUsingName(search);
        }
    }
}
