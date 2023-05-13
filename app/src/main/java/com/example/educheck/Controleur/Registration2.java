package com.example.educheck.Controleur;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.educheck.Modele.Implementation.InscriptionImplementation;
import com.example.educheck.Modele.Interface.AsyncTaskcallback;
import com.example.educheck.Modele.Interface.Inscription;
import com.example.educheck.Modele.Student;
import com.example.educheck.Modele.University;
import com.example.educheck.R;

import org.json.JSONArray;
import org.json.JSONException;


public class Registration2 extends AppCompatActivity implements AsyncTaskcallback {
    private TextView TextView_AddInformation;
    private TextView TextView_EmailIneStatus;
    private EditText EditText_email;
    private EditText EditText_INE;
    private EditText EditText_status;
    private Button Button_Submit;
    private Student student;
    private University university;
    private InscriptionImplementation inscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration2);

        TextView_EmailIneStatus = findViewById(R.id.main_textviewEmailIneStatus);
        TextView_AddInformation = findViewById(R.id.main_textviewAddInformation);
        EditText_email = findViewById(R.id.main_EditTextemail);
        EditText_INE = findViewById(R.id.main_EditTextIne);
       // EditText_status = findViewById(R.id.main_EditTextStatus);
        Button_Submit = findViewById(R.id.main_button_Submit);
        Button_Submit.setEnabled(false);

        EditText_INE.addTextChangedListener(emailIneStatusWatcher);
        EditText_email.addTextChangedListener(emailIneStatusWatcher);
        EditText_status.addTextChangedListener(emailIneStatusWatcher);
        student = (Student) getIntent().getSerializableExtra("student");
        inscription = new InscriptionImplementation(this);
        university = (University) getIntent().getSerializableExtra("university") ;

        Button_Submit.setOnClickListener(v -> {
           student.setIne( EditText_INE.getText().toString());
           student.setStatus(EditText_status.getText().toString());
           student.setMail(EditText_email.getText().toString());
           inscription.registerOnUniversity(university,student);

        });
    }
        private final TextWatcher  emailIneStatusWatcher= new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Button_Submit.setEnabled(EditText_INE.getText().length() > 9
                        && Patterns.EMAIL_ADDRESS.matcher(EditText_email.getText()).matches()
                );
            }
        };

    @Override
    public void onTaskCompleted(JSONArray items) throws JSONException {

    }
}







