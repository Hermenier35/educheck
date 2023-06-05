package com.example.educheck.Controleur.Login;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.educheck.Controleur.Dashboard.DashBoardEtudiant;
import com.example.educheck.Controleur.DashbardTeacher.DashBoardTeacher;
import com.example.educheck.Controleur.Dashboard.FragMessages1;
import com.example.educheck.Controleur.DashboardAdmin.DashboardAdmin;
import com.example.educheck.Modele.Implementation.LoginImplementation;
import com.example.educheck.Modele.Interface.AsyncTaskcallback;
import com.example.educheck.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;


public class Login extends AppCompatActivity implements AsyncTaskcallback {
    Button login;
    EditText email;
    EditText password;
    LoginImplementation model_login;

    TextView forgottenPassword;
    Intent forgottenPasswordActivity;
    Intent dashboard;

    String mail;

    String pass;
    @Override
    protected void onCreate(Bundle save) {

        super.onCreate(save);
        setContentView(R.layout.activity_login);
        login = findViewById(R.id.sign_in);
        email = findViewById(R.id.username);
        password = findViewById(R.id.password);
        forgottenPasswordActivity = new Intent(this, ForgotPassword.class);
        forgottenPassword = findViewById(R.id.forgetten);
        model_login = new LoginImplementation(this);
        login.setOnClickListener(v -> login_verification());

        email.addTextChangedListener(emailWatcher);
        password.addTextChangedListener(emailWatcher);

        forgottenPassword.setOnClickListener(v -> {
            startActivity(forgottenPasswordActivity);
        });


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
        login.setEnabled(password.getText().length()>=8
                && Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches()
        );
        }
    };

    protected void login_verification(){
        mail=email.getText().toString();
        pass=password.getText().toString();
        model_login.connexion(mail,pass);
    }
    @Override
    public void onTaskCompleted(JSONArray items) throws JSONException {
        Objects.requireNonNull(items); //items ne doit pas être null
        JSONObject response = items.getJSONObject(0);
        if(!response.getBoolean("statut")){
            Toast.makeText(this,"Invalid email or password", Toast.LENGTH_SHORT).show();
        }else{
            switch(response.getString("status")){
                case "Admin":
                    dashboard = new Intent(this, DashboardAdmin.class);
                    dashboard.putExtra("token", response.getString("token"));
                    dashboard.putExtra("valide", response.getBoolean("valide"));
                    startActivity(dashboard);
                    break;
                case "Student":
                    dashboard = new Intent(this, DashBoardEtudiant.class);
                    if(!response.getBoolean("valide"))
                        Toast.makeText(this, "please wait teacher's confirmation", Toast.LENGTH_SHORT).show();
                    else {
                        dashboard.putExtra("token", response.getString("token"));
                        dashboard.putExtra("mail", mail);
                        startActivity(dashboard);
                    }
                    break;
                case "Teacher":
                    dashboard = new Intent(this, DashBoardTeacher.class);
                    dashboard.putExtra("token", response.getString("token"));
                    startActivity(dashboard);
                    break;
                default: System.err.println("Erreur retour status"); System.exit(1);
            }
        }
    }




    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}