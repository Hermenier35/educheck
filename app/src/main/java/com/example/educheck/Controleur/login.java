package com.example.educheck.Controleur;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.educheck.Modele.Interface.AsyncTaskcallback;
import com.example.educheck.Modele.Interface.Login;
import com.example.educheck.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class login extends AppCompatActivity implements AsyncTaskcallback {
    Button login;
    EditText email;
    EditText password;
    Login model_login;
    @Override
    protected void onCreate(Bundle save) {

        super.onCreate(save);
        setContentView(R.layout.activity_login);
        login = findViewById(R.id.sign_in);
        email = findViewById(R.id.username);
        password = findViewById(R.id.password);

        login.setOnClickListener(v -> login_verification());

        email.addTextChangedListener(emailWatcher);
        password.addTextChangedListener(emailWatcher);

        }
    private final TextWatcher emailWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
        login.setEnabled(password.getText().length()>8
                && Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches()
        );
        }
    };

    protected void login_verification(){
        model_login.connexion(email.getText().toString(),password.getText().toString());
    }
    @Override
    public void onTaskCompleted(JSONArray items) throws JSONException {
        JSONObject response = items.getJSONObject(0);
        if(!response.getBoolean("status")){
            Toast.makeText(this,"Invalid email or password", Toast.LENGTH_SHORT).show();
        }
    }
}