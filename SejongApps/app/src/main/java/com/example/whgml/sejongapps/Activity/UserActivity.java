package com.example.whgml.sejongapps.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.whgml.sejongapps.R;

public class UserActivity extends Fragment implements View.OnClickListener{

    private ImageView mapActivityBtn;
    private ImageView tabbedActivityBtn;
    private ImageView sqlBtn;
    private ImageView tableBtn;

    private Intent tabbedActivityIntent;
    private Intent sqlIntent;
    private Intent mapActivityIntent;
    private Intent tableIntent;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.user_activity);
//        initialize();
//    }
    private View myView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.user_activity, container, false);
        initialize();
        return myView;
    }

    private void initialize()
    {
        mapActivityBtn = (ImageView)myView.findViewById(R.id.mapActivityButton);
        tabbedActivityBtn = (ImageView)myView.findViewById(R.id.tabbedActivityButton);
        sqlBtn = (ImageView)myView.findViewById(R.id.sqlButton);
        tableBtn = (ImageView)myView.findViewById(R.id.tableButton);

        sqlBtn.setOnClickListener(this);
        tableBtn.setOnClickListener(this);
        mapActivityBtn.setOnClickListener(this);
        tabbedActivityBtn.setOnClickListener(this);

        sqlIntent = new Intent(getContext(), QueryActivity.class);
        tableIntent = new Intent(getContext(), TableActivity.class);
        mapActivityIntent = new Intent(getContext(), MapsActivity.class);
        tabbedActivityIntent = new Intent(getContext(), TabbedActivity.class);
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
        else if(id == R.id.mapActivityButton)
        {
            startActivity(mapActivityIntent);
        }
        else if(id == R.id.tabbedActivityButton)
        {
            startActivity(tabbedActivityIntent);
        }
    }
}
