package com.example.whgml.sejongapps.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.whgml.sejongapps.Model.User;
import com.example.whgml.sejongapps.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class InfoDisplayActivity extends AppCompatActivity {

    private TextView textViewStudentFullName;
    private TextView textViewStudentId;
    private TextView textViewStudentEmail;
    private TextView textViewStudentAge;
    private TextView textViewStudentGraduation;
    private TextView textViewStudentCourses;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_display);
        mAuth = FirebaseAuth.getInstance();
        initialize();
    }

    private void initialize() {
        Bundle bundle = getIntent().getExtras();
        textViewStudentFullName = (TextView)findViewById(R.id.textView_studentFullNameEntry);
        textViewStudentId = (TextView)findViewById(R.id.textView_studentIdEntry);
        textViewStudentEmail = (TextView)findViewById(R.id.textView_studentEmailEntry);
        textViewStudentAge = (TextView)findViewById(R.id.textView_studentAgeEntry);
        textViewStudentGraduation = (TextView)findViewById(R.id.textView_studentEducationGradeEntry);
        textViewStudentCourses = (TextView)findViewById(R.id.textView_studentPassedCoursesEntry);
        User userInfo = new User();
        userInfo.setName(bundle.getCharSequence("fullName").toString());
        userInfo.setAge(bundle.getCharSequence("age").toString());
        userInfo.setEmail(bundle.getCharSequence("email").toString());
        String graduation = bundle.getCharSequence("graduation").toString();
        String courses = bundle.getCharSequence("courses").toString();
        String stid = bundle.getCharSequence("stId").toString();

        textViewStudentFullName.setText(userInfo.getName());
        textViewStudentId.setText(stid);
        textViewStudentEmail.setText(userInfo.getEmail());
        textViewStudentAge.setText(userInfo.getAge());
        textViewStudentGraduation.setText(graduation);
        textViewStudentCourses.setText(courses);

        FirebaseUser user = mAuth.getCurrentUser();

        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("info");
        mRef = mRef.child(user.getUid());
        DatabaseReference userRef = mRef.child("name");
        userRef.setValue(userInfo.getName());
        userRef = mRef.child("age");
        userRef.setValue(userInfo.getAge());
        userRef = mRef.child("email");
        userRef.setValue(userInfo.getEmail());
        userRef = mRef.child("graduation");
        userRef.setValue(graduation);
        userRef = mRef.child("courses");
        userRef.setValue(courses);
        userRef = mRef.child("stid");
        userRef.setValue(stid);
    }
}
