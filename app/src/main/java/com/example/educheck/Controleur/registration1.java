package com.example.educheck.Controleur;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.educheck.R;

public class registration1 extends AppCompatActivity {
    private TextView TextView1;
    private TextView TextView2;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration1);
        TextView1 =findViewById(R.id.main_textview_Name);
        TextView2 =findViewById(R.id.main_textviewLandFName);
        firstNameEditText =findViewById(R.id.main_textviewFirstName);
        lastNameEditText=findViewById(R.id.main_textviewLastName);
        nextButton=findViewById(R.id.main_button_Inscription);
        nextButton.setEnabled(false);
        firstNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        }
        );
        lastNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        }
        );

    }




}
