package com.example.alfred.ui.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.alfred.R;

public class Register extends AppCompatActivity {

    EditText name;
    EditText mail;
    EditText password1;
    EditText password2;
    RadioButton termsUse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.nameUser);
        mail = findViewById(R.id.mail);
        password1 = findViewById(R.id.password);
        password2 = findViewById(R.id.password2);
        termsUse = findViewById(R.id.termsUse);
    }

    public void goToLogin (View view) {

        String nameUser = name.getText().toString();
        String mailUser = mail.getText().toString();
        String passw1 = password1.getText().toString();
        String passw2 = password2.getText().toString();



        if (nameUser.isEmpty()){
            Toast.makeText(getApplication(), "Debes de poner tu nombre", Toast.LENGTH_LONG).show();
        } else if(mailUser.isEmpty()) {
            Toast.makeText(getApplication(), "Debes de poner un mail", Toast.LENGTH_LONG).show();
        } else if (passw1.isEmpty()) {
            Toast.makeText(getApplication(), "El campo de la contraseña esta vacio", Toast.LENGTH_LONG).show();
        }else if (passw1.equals(passw2)) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            Toast.makeText(getApplication(), "Registrado correctamente", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(getApplication(), "Las contraseñas no son iguales", Toast.LENGTH_LONG).show();
        }
    }
}