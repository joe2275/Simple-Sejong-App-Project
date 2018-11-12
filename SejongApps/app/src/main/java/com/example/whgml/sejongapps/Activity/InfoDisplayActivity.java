package com.example.whgml.sejongapps.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.whgml.sejongapps.R;

import org.w3c.dom.Text;

public class InfoDisplayActivity extends AppCompatActivity {

    private TextView textViewStudentFullName;
    private TextView textViewStudentId;
    private TextView textViewStudentEmail;
    private TextView textViewStudentAge;
    private TextView textViewStudentGraduation;
    private TextView textViewStudentCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_display);
        initialize();
    }

    private void initialize() {
        Bundle bundle = getIntent().getExtras();
        textViewStudentFullName = (TextView)findViewById(R.id.textView_studentFullNameEntry);
        textViewStudentId = (TextView)findViewById(R.id.textView_studentIdEntry);
        textViewStudentEmail = (TextView)findViewById(R.id.textView_studentEmailEntry);
        textViewStudentAge = (TextView)findViewById(R.id.textView_studentAge);
        textViewStudentGraduation = (TextView)findViewById(R.id.textView_studentEducationGradeEntry);
        textViewStudentCourses = (TextView)findViewById(R.id.textView_studentPassedCoursesEntry);

        textViewStudentFullName.setText(bundle.getCharSequence("fullName"));
        textViewStudentId.setText(bundle.getCharSequence("stId"));
        textViewStudentEmail.setText(bundle.getCharSequence("email"));
        textViewStudentAge.setText(bundle.getCharSequence("age"));
        textViewStudentGraduation.setText(bundle.getCharSequence("graduation"));
        textViewStudentCourses.setText(bundle.getCharSequence("courses"));
    }
}
