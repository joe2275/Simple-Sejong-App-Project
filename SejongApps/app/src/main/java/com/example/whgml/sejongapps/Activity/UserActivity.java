package com.example.whgml.sejongapps.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.whgml.sejongapps.Helper.Calculator;
import com.example.whgml.sejongapps.Model.PrimeActivity;
import com.example.whgml.sejongapps.R;

public class UserActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView calculatorBtn;
    private ImageView primeNumBtn;
    private ImageView sqlBtn;
    private ImageView tableBtn;

    private Intent primeIntent;
    private Intent sqlIntent;
    private Intent calcIntent;
    private Intent tableIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity);
        initialize();
    }

    private void initialize()
    {
        calculatorBtn = (ImageView)findViewById(R.id.calculatorButton);
        primeNumBtn = (ImageView)findViewById(R.id.primeNumButton);
        sqlBtn = (ImageView)findViewById(R.id.sqlButton);
        tableBtn = (ImageView)findViewById(R.id.tableButton);

        sqlBtn.setOnClickListener(this);
        tableBtn.setOnClickListener(this);
        calculatorBtn.setOnClickListener(this);
        primeNumBtn.setOnClickListener(this);

        sqlIntent = new Intent(this, QueryActivity.class);
        tableIntent = new Intent(this, TableActivity.class);
        calcIntent = new Intent(this, CalculatorActivity.class);
        primeIntent = new Intent(this, PrimeActivity.class);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if(id == R.id.sqlButton)
        {
            startActivity(sqlIntent);
        }
        else if(id == R.id.tableButton)
        {
            startActivity(tableIntent);
        }
        else if(id == R.id.calculatorButton)
        {
            startActivity(calcIntent);
        }
        else if(id == R.id.primeNumButton)
        {
            startActivity(primeIntent);
        }
    }
}
