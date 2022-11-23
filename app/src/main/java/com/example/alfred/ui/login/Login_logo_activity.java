package com.example.alfred.ui.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.alfred.R;
import com.example.alfred.ui.Espacios.SalaPrincipal;

import utils.PreferenceUtils;

public class Login_logo_activity  extends AppCompatActivity {

    Button btn_login;
    Button btn_register;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_logo);

        btn_login = findViewById(R.id.btn_iniciar_sesion);
        btn_register = findViewById(R.id.btn_registrarse);

        comprobarSharedPrefs();
    }

    public  void GotoLogin (View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public  void GotoRegister(View view){
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    public  void GotoEspacio(View view){
        Intent intent = new Intent(this, EspacioActivity.class);
        startActivity(intent);
    }

    public void comprobarSharedPrefs(){

        try {
            if (PreferenceUtils.getEmail(this) != null || !PreferenceUtils.getEmail(this).equals("")){
                Intent intent = new Intent(this, SalaPrincipal.class);
                startActivity(intent);
            }
        } catch (Exception a){

        }

    }
}
