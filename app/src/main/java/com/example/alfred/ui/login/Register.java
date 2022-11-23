package com.example.alfred.ui.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.alfred.R;
import com.example.alfred.ui.Espacios.SalaPrincipal;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    EditText name, mail, password1, password2;
    RadioButton termsUse;

    ImageButton imageButton;
    Button register;

    RequestQueue requesQueue;
    //ponemos la direccion IP de nuestro ordenador
      private static final String URL1="https://unscholarly-princip.000webhostapp.com/register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        requesQueue = Volley.newRequestQueue(this);

        //Creacion de variables
        mail = findViewById(R.id.mail);
        password1 = findViewById(R.id.password);
        password2 = findViewById(R.id.password2);
        name = findViewById(R.id.name);
        register = findViewById(R.id.register);
        imageButton = findViewById(R.id.goToLogin);

        //Metodo on click para cerrar la actividad y volver al login
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Register.this.finish();;
            }
        });
    }

    // Metodo que hace la validacion de los datos introducidos por pantalla
    public void doRegistrer (View view) {
        // instanciamos las variables
        String nameUser = name.getText().toString();
        String mailUser = mail.getText().toString();
        String passw1 = password1.getText().toString();
        String passw2 = password2.getText().toString();

        // logica de validacion de los datos
        if (nameUser.isEmpty() && mailUser.isEmpty() && passw1.isEmpty() && passw2.isEmpty()) {
            Toast.makeText(getApplication(), "Debes de rellenar los datos", Toast.LENGTH_LONG).show();
        }else if (nameUser.isEmpty()){
            Toast.makeText(getApplication(), "Debes de poner tu nombre", Toast.LENGTH_LONG).show();
        } else if(mailUser.isEmpty()) {
            Toast.makeText(getApplication(), "Debes de poner un mail", Toast.LENGTH_LONG).show();
        } else if (passw1.isEmpty()) {
            Toast.makeText(getApplication(), "El campo de la contraseña esta vacio", Toast.LENGTH_LONG).show();
        }else if (passw1.equals(passw2)) {
            createRegister(nameUser, mailUser, passw1, passw2);     // Al entrar aqui pasa al metodo de crear un usuario
        }else {
            Toast.makeText(getApplication(), "Las contraseñas no son iguales", Toast.LENGTH_LONG).show();
        }
    }


    //Metodo para registrarnos pasando las variables al host y ejecutar el php
    public void createRegister(final String name,final String mail,final String passw1, final String passw2){


        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Por favor espera...");
        progressDialog.show();

        //Hacemos la peticion al servidor
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();

                        //Si recibimos este mensaje del php significa que nos hemos registrado con exito
                        if(response.contains("Usuarios registrado")){
                            //Vaciamos las variables por seguridad
                            Register.this.name.setText("");
                            Register.this.mail.setText("");
                            Register.this.password1.setText("");
                            Register.this.password2.setText("");
                            // Volvemos a la pantalla del login
                            Intent intent = new Intent(Register.this, LoginActivity.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(Register.this, response, Toast.LENGTH_SHORT).show();
                            Log.d("el error es:", response);
                        }
                    }
                },
                new Response.ErrorListener(){

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(Register.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("username",name);
                params.put("password", passw1);
                params.put("password2", passw2);
                params.put("email",mail);
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Register.this);
        requestQueue.add(stringRequest);
    }


    public void goToLogin (View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
