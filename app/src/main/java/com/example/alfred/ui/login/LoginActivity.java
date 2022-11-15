package com.example.alfred.ui.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.alfred.R;
import com.example.alfred.ui.HomeActivity;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText userName, password;
    private String strUserName, strPassword;

    //URL del archivo php de nuestro LOGIN
    private static final String URL2="http://192.168.0.14/alfred/login.php";



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
        finish();
    }


    public void goToForgotPassword (View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public void goToHome (View view) {
        if(userName.getText().toString().equals("")){
            Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show();
        }
        else if(password.getText().toString().equals("")){
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
        }
        else{
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Por favor espera...");
            progressDialog.show();

            strUserName = userName.getText().toString().trim();
            strPassword = password.getText().toString().trim();


            StringRequest request = new StringRequest(Request.Method.POST, URL2, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();

                    if(response.equalsIgnoreCase("Login Correcto")){
                        userName.setText("");
                        password.setText("");
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                        //startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        //startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        //Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                    }
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
            },new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    params.put("email",strUserName);
                    params.put("password",strPassword);
                    return params;

                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
            requestQueue.add(request);
        }
    }
}