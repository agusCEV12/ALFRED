package com.example.alfred.ui.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.alfred.R;
import com.example.alfred.ui.HomeActivity;
import com.example.alfred.ui.recovery_pass.RecoverPassActivity;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    //private LoginViewModel loginViewModel;
   // private ActivityLoginBinding binding;

    private EditText userName, password;
    private String strUserName, strPassword;
    //URL del archivo php de nuestro LOGIN
    private static final String URL1="http://192.168.0.14/alfred/login.php";
    private Button btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userName = findViewById(R.id.et_username);
        password = findViewById(R.id.et_password);

    }


    // navegacion pantalla formulario
    public void goToRegister (View view) {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }


    public void goToHome (View view){
        // ponemos logica de login
        // si no se ha introducido correo o correo
        if (userName.getText().toString().equals("")) {
            Toast.makeText(this, "Debes de introducir un correo", Toast.LENGTH_SHORT).show();
        } else if(password.getText().toString().equals("")){
            Toast.makeText(this, "Debes de introducir la contraseña", Toast.LENGTH_SHORT).show();
        } else {
            //salga este mensaje mientras se espera
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Por favor espere");
            progressDialog.show();

            strUserName = userName.getText().toString().trim();
            strPassword = password.getText().toString().trim();

            StringRequest request = new StringRequest(Request.Method.POST, URL1, response -> {
                progressDialog.dismiss();

                if (response.equalsIgnoreCase("Login Correcto")) {
                    userName.setText("");
                    password.setText("");
                    //Mandamos a la actividad de Home

                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                } else {
                    Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
                }
            }, error -> {
                progressDialog.dismiss();
                Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String,String> params = new HashMap<>();
                    params.put("email", strUserName);
                    params.put("password", strPassword);

                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(request);

        }
    }


    public void goToForgotPassword (View view) {
        Intent intent = new Intent(this, RecoverPassActivity.class);
        startActivity(intent);
    }
}

  /*   NOSE  SI VALE
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        Toast.makeText(LoginActivity.this, "Vamos a la app", Toast.LENGTH_SHORT).show();
        //Terminar la actividad si se entra bien
        finish();*/