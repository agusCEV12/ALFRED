package com.example.alfred.ui.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.alfred.R;
import com.example.alfred.ui.recovery_pass.RecoverPassActivity;

public class Login_logo_activity  extends AppCompatActivity {

    Button btn_login;
    Button btn_register;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_logo);

        btn_login = findViewById(R.id.btn_iniciar_sesion);
        btn_register = findViewById(R.id.btn_registrarse);
    }

    public  void GotoLogin (View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public  void GotoRegister(View view){
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }
}
