package com.example.alfred.ui.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

import utils.PreferenceUtils;

public class HomeActivity extends AppCompatActivity {

    String URL = "https://unscholarly-princip.000webhostapp.com/addHome.php";
    EditText nameHouseET;
    String nameUser;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espacio);
        nameHouseET = findViewById(R.id.et_name_home);

        Bundle extra = getIntent().getExtras();
        if (extra != null){
            nameUser = extra.getString("user");
        } else{
            nameUser = PreferenceUtils.getEmail(this);
        }

    }

    public void createHome (View view){
        String nameHouse = nameHouseET.getText().toString();

        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("Casa Creada")){
                    String nameUser ;
                    nameHouseET.setText("");
                    PreferenceUtils.saveHome(nameHouseET.getText().toString(), HomeActivity.this);
                    Intent intent = new Intent(HomeActivity.this, SalaPrincipal.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(HomeActivity.this,"Este usuario ya tiene una casa", Toast.LENGTH_LONG).show();
                    Log.d("el error es:", response);
                }
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(HomeActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("emailUser", nameUser);
                params.put("name", nameHouse);
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);
        requestQueue.add(request);
    }
}