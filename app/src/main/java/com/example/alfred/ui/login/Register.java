package com.example.alfred.ui.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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
import com.example.alfred.ui.ToolbarActivity;
import com.example.alfred.ui.login.LoginActivity;

import java.util.HashMap;
import java.util.Map;

public class Register extends ToolbarActivity {
    EditText name, mail, password1, password2;
    RadioButton termsUse;

    RequestQueue requesQueue;
    //ponemos la direccion IP de nuestro ordenador
   // private static final String URL1="http://192.168.0.14/alfred/save.php";
    private static final String URL1="http://192.168.0.14/alfred/save.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        requesQueue = Volley.newRequestQueue(this);

        mail = findViewById(R.id.mail);
        password1 = findViewById(R.id.password);
        password2 = findViewById(R.id.password2);
    }

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
            //volver al HOME
            //Intent intent = new Intent(this, LoginActivity.class);
            //startActivity(intent);
            //Toast.makeText(getApplication(), "Registrado correctamente", Toast.LENGTH_LONG).show();
            createUser(nameUser, mailUser, passw1);
        }else {
            Toast.makeText(getApplication(), "Las contraseñas no son iguales", Toast.LENGTH_LONG).show();
        }
    }

    private void createUser(final String name,final String mail,final String passw1){

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        Toast.makeText(getApplication(), "Registrado correctamente ", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(final VolleyError error) {
                        Toast.makeText(getApplication(), "Algo ha pasado", Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("email", mail);
                params.put("password", passw1);
                return params;
            }
        };

        requesQueue.add(stringRequest);
    }

    public void goToLogin (View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}