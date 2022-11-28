package com.example.alfred.ui.login;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.alfred.R;
import com.example.alfred.ui.Espacios.SalaPrincipal;
import com.example.alfred.ui.ui.home.HomeActivity;
//import com.example.alfred.ui.HomeActivity;

import java.util.HashMap;
import java.util.Map;

import utils.PreferenceUtils;

public class LoginActivity extends AppCompatActivity {

    private EditText userName, password;
    private String strUserName, strPassword;
    ImageButton imageButton;
    //URL del archivo php de nuestro LOGIN
    //private static final String URL2="http://192.168.0.14/alfred/login.php";
    private static  final String URL2 ="https://unscholarly-princip.000webhostapp.com/login.php";
    private static  final String URL3 ="https://unscholarly-princip.000webhostapp.com/checkHome.php";

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userName = findViewById(R.id.et_username);
        password = findViewById(R.id.et_password);

        imageButton = findViewById(R.id.buttonBack);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.this.finish();
            }
        });

    }
    //hj
    // navegacion pantalla formulario
    public void goToRegister (View view) {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
        finish();
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
                    if(response.contains("Success")){
                        PreferenceUtils.saveEmail(strUserName, LoginActivity.this);
                        PreferenceUtils.savePassword(strPassword, LoginActivity.this);
                        checkHome();
                    }
                    else{
                        Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                    }
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

    public void checkHome(){
        StringRequest request = new StringRequest(Request.Method.POST, URL3, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("This user already has a home")){
                    Toast.makeText(LoginActivity.this, "Holiwi", Toast.LENGTH_SHORT).show();
                    userName.setText("");
                    Intent intent = new Intent(LoginActivity.this, SalaPrincipal.class);
                    intent.putExtra("user", strUserName);
                    startActivity(intent);
                }else if (response.contains("This user doesnt has a home")){
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                }
            }
        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("email",strUserName);
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        requestQueue.add(request);
    }
}