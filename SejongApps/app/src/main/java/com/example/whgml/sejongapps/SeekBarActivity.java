package com.example.whgml.sejongapps;

import android.content.ComponentName;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SeekBarActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText nameEdit;
    private EditText emailEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seek_bar);
    }

    @Override
    public void onClick(View view)
    {

    }
}
