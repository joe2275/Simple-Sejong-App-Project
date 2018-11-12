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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.whgml.sejongapps.R;

public class RadioButtonsActivity extends Fragment implements View.OnClickListener {

    private EditText editTextStudentFullName;
    private EditText editTextStudentId;
    private EditText editTextStudentEmail;
    private EditText editTextStudentAge;
    private RadioGroup radioGroupGraduationInfo;
    private RadioButton radioButtonStudentUndergraduate;
    private RadioButton radioButtonStudentGraduate;
    private CheckBox checkBoxCourseMobileProgramming;
    private CheckBox checkBoxCourseDatabase;
    private CheckBox checkBoxCourseCommunications;
    private CheckBox checkBoxCourseOperatingSystems;
    private Button buttonSubmit;
    private Intent infoDisplayIntent;

    private StringBuilder checkedStudentCourses;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_radio_buttons);
//        initialize();
//    }

    private View myView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.calculator_activity, container, false);
        initialize();
        return myView;
    }

    @Override
    public void onClick(View v) {
        System.out.println("hello");
        switch(v.getId()) {
            case R.id.button_submit :
                if(checkBoxCourseMobileProgramming.isChecked()) {
                    checkedStudentCourses.append(getString(R.string.course_mobile_programming +'\n'));
                }
                if(checkBoxCourseDatabase.isChecked()) {
                    checkedStudentCourses.append(getString(R.string.course_database + '\n'));
                }
                if(checkBoxCourseCommunications.isChecked()) {
                    checkedStudentCourses.append(getString(R.string.course_communications + '\n'));
                }
                if(checkBoxCourseOperatingSystems.isChecked()) {
                    checkedStudentCourses.append(getString(R.string.course_operating_systems + '\n'));
                }
                String graduation = "";
                int radioGroupId = radioGroupGraduationInfo.getCheckedRadioButtonId();
                if(radioGroupId == radioButtonStudentUndergraduate.getId()) {
                    graduation = getString(R.string.grade_undergraduate);
                }
                else if(radioGroupId == radioButtonStudentGraduate.getId()) {
                    graduation = getString(R.string.grade_graduate);
                }

                Bundle bundle = new Bundle();

                bundle.putString("fullName", editTextStudentFullName.getText().toString());
                bundle.putString("stId", editTextStudentId.getText().toString());
                bundle.putString("email", editTextStudentEmail.getText().toString());
                bundle.putString("age", editTextStudentAge.getText().toString());
                bundle.putString("graduation", graduation);
                bundle.putString("courses", checkedStudentCourses.toString());
                infoDisplayIntent.putExtras(bundle);
                startActivity(infoDisplayIntent);
                break;
        }
    }

    private void initialize()
    {
        infoDisplayIntent = new Intent(myView.getContext(), InfoDisplayActivity.class);
        checkedStudentCourses = new StringBuilder();
        editTextStudentFullName = (EditText)myView.findViewById(R.id.editText_studentFullName);
        editTextStudentId = (EditText)myView.findViewById(R.id.editText_studentId);
        editTextStudentEmail = (EditText)myView.findViewById(R.id.editText_studentEmail);
        editTextStudentAge = (EditText)myView.findViewById(R.id.editText_studentAge);
        radioGroupGraduationInfo = (RadioGroup)myView.findViewById(R.id.radioGroup_graduationInfo);
        radioButtonStudentUndergraduate = (RadioButton)myView.findViewById(R.id.radioButton_undergraduate);
        radioButtonStudentGraduate = (RadioButton)myView.findViewById(R.id.radioButton_graduate);
        checkBoxCourseMobileProgramming = (CheckBox)myView.findViewById(R.id.checkBox_mobileProgramming);
        checkBoxCourseDatabase = (CheckBox)myView.findViewById(R.id.checkBox_database);
        checkBoxCourseCommunications = (CheckBox)myView.findViewById(R.id.checkBox_communications);
        checkBoxCourseOperatingSystems = (CheckBox)myView.findViewById(R.id.checkBox_operatingSystems);
        buttonSubmit = (Button)myView.findViewById(R.id.button_submit);
        myView.setOnClickListener(this);
        buttonSubmit.setOnClickListener(this);
    }
}
