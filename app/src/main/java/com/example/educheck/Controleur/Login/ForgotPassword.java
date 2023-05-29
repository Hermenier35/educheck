package com.example.educheck.Controleur.Login;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.educheck.Modele.Implementation.LoginImplementation;
import com.example.educheck.Modele.Interface.AsyncTaskcallback;
import com.example.educheck.Modele.Interface.Login;
import com.example.educheck.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class ForgotPassword extends AppCompatActivity implements AsyncTaskcallback {

    Button reset;
    Button my_button;
    EditText email;
    Login password;
    LoginImplementation model_forgot;

    protected void onCreate(Bundle save) {

        super.onCreate(save);
        setContentView(R.layout.forgot_password);
        reset = findViewById(R.id.reset);

        email = findViewById(R.id.username2);
        model_forgot = new LoginImplementation(this);
        reset.setOnClickListener(v -> email_verification());

        email.addTextChangedListener(emailWatcher);
    }

    protected void email_verification(){
        model_forgot.forgetPassword(email.getText().toString());
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

    public void onTaskCompleted(JSONArray items) throws JSONException {
        Objects.requireNonNull(items);
        JSONObject response = items.getJSONObject(0);
        if(!response.getBoolean("status")){
            Toast.makeText(this,"Invalid email", Toast.LENGTH_SHORT).show();
        }else{
            if(!response.getBoolean("valide"))
                Toast.makeText(this, "please wait teacher's confirmation", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "check you mail, we have sent you a password", Toast.LENGTH_SHORT).show();
        }
    }



}


