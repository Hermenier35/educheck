package com.example.educheck.Controleur;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.educheck.Modele.AsyncTaskcallback;
import com.example.educheck.R;


public class Registration2 extends AppCompatActivity {
    private TextView TextView_AddInformation;
    private TextView TextView_EmailIneStatus;
    private EditText EditText_email;
    private EditText EditText_INE;
    private EditText EditText_status;
    private Button Button_Submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration2);
        TextView_EmailIneStatus=findViewById(R.id.main_textviewEmailIneStatus);
        TextView_AddInformation=findViewById(R.id.main_textviewAddInformation);
        EditText_email=findViewById(R.id.main_EditTextemail);
        EditText_INE=findViewById(R.id.main_EditTextemail);
        EditText_status=findViewById(R.id.main_EditTextStatus);
        Button_Submit=findViewById(R.id.main_button_Submit);
        Button_Submit.setEnabled(false);
        EditText_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>3)
                    Button_Submit.setEnabled(!s.toString().isEmpty());
            }
        }
        );




    }


}
