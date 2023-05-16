package com.example.educheck.Controleur;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.educheck.Modele.Interface.AsyncTaskcallback;
import com.example.educheck.Modele.Interface.Login;
import com.example.educheck.R;

import org.json.JSONArray;
import org.json.JSONException;

public class forgot_password extends AppCompatActivity implements AsyncTaskcallback {

    Button reset;
    Button my_button;
    EditText email;
    Login password;

    protected void onCreate(Bundle save) {

        super.onCreate(save);
        setContentView(R.layout.forgot_password);
        reset = findViewById(R.id.reset);

        email = findViewById(R.id.username2);
        reset.setOnClickListener(v -> email_verification());

        email.addTextChangedListener(emailWatcher);

    }

    protected void email_verification(){
        password.forgetPassword(email.getText().toString());
        }

    private final TextWatcher emailWatcher = new TextWatcher() {
        //Un TextWatcher est une interface qui écoute les changements de texte dans un champ de texte.
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            reset.setEnabled(Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches());
        }       // setEnabled active ou désactive le bouton reset
    };
    @Override
    public void onTaskCompleted(JSONArray items) throws JSONException {

    }


}


