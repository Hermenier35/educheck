package com.example.educheck.Controleur.Registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.educheck.Modele.Student;
import com.example.educheck.Modele.University;
import com.example.educheck.R;

public class registration1 extends AppCompatActivity {
    private TextView TextView_Name;
    private TextView TextViewLandFName;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private Button nextButton;

    private Intent intentRegistration2;
    private University university;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration1);

        TextView_Name = findViewById(R.id.main_textview_Name);
        TextViewLandFName = findViewById(R.id.main_textviewLandFName);
        firstNameEditText = findViewById(R.id.main_textviewFirstName);
        lastNameEditText = findViewById(R.id.main_textviewLastName);
        nextButton = findViewById(R.id.main_button_Inscription);

        nextButton.setEnabled(false);
        intentRegistration2 = new Intent(getApplicationContext(), Registration2.class);
        firstNameEditText.addTextChangedListener(NameWatcher);
        lastNameEditText.addTextChangedListener(NameWatcher);

        university = (University) getIntent().getSerializableExtra("university") ;
        Student student =new Student("","","","","");

        nextButton.setOnClickListener(v -> {
            student.setFirstName(firstNameEditText.getText().toString());
            student.setLastName(lastNameEditText.getText().toString());
            intentRegistration2.putExtra("student", student);
            intentRegistration2.putExtra("university",university);
            startActivity(intentRegistration2);
        });



    }
        private final TextWatcher  NameWatcher= new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                nextButton.setEnabled(firstNameEditText.getText().length() > 3
                        && lastNameEditText.getText().length()>3
                );
            }
        };


    }


