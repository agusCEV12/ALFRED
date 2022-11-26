package com.example.alfred.ui.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.example.alfred.ui.ui.home.HomeActivity;

import java.util.HashMap;
import java.util.Map;

import utils.PreferenceUtils;

public class Login_logo_activity  extends AppCompatActivity  {

    Button btn_login;
    Button btn_register;

    String URL = "https://unscholarly-princip.000webhostapp.com/checkHome.php";

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_logo);

        btn_login = findViewById(R.id.btn_iniciar_sesion);
        btn_register = findViewById(R.id.btn_registrarse);

        //Log.d("email es:", PreferenceUtils.getEmail(this));
        //Log.i("la pass es: ", PreferenceUtils.getPassword(this));

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

        boolean check;
        try {
           checkHome();
        } catch (Exception a){

        }
    }
//--------------------------------------------------------------------------------------------------------------------------
    // Comprobamos si el email tiene asociada una casa y nos movemos en funcion del resultado
    public void checkHome (){
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("This user already has a home")
                && (PreferenceUtils.getEmail(Login_logo_activity.this) != null ||
                        PreferenceUtils.getEmail(Login_logo_activity.this).equals(""))){

                    Intent intent = new Intent(Login_logo_activity.this, SalaPrincipal.class);
                    startActivity(intent);

                }else if (response.contains("This user doesnt has a home")&&
                        (PreferenceUtils.getEmail(Login_logo_activity.this) != null) ||
                PreferenceUtils.getEmail(Login_logo_activity.this).equals("")){

                   Intent intent = new Intent(Login_logo_activity.this, HomeActivity.class);
                   startActivity(intent);
                }
                else{
                    Toast.makeText(Login_logo_activity.this, response, Toast.LENGTH_SHORT).show();
                    Log.d("Hola", "No compruebo nada");
                }
            }
        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login_logo_activity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("email",PreferenceUtils.getEmail(Login_logo_activity.this));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

}
