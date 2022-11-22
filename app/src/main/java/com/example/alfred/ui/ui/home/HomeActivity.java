package com.example.alfred.ui.ui.home;

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

import java.util.HashMap;
import java.util.Map;

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
                    /*Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    intent.putExtra("user", strUserName);
                    startActivity(intent);*/
                    //Toast.makeText(HomeActivity.this, response, Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(HomeActivity.this, response, Toast.LENGTH_SHORT).show();
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

  /*  String nameHouse = nameHouseET.getText().toString();

    StringRequest request = new StringRequest(
            Request.Method.POST,
            URL,
            response -> Toast.makeText(getApplication(), "Casa creada ", Toast.LENGTH_LONG).show(),
            error -> Toast.makeText(getApplication(), "UPS error 5151651451", Toast.LENGTH_LONG).show()){

        @Nullable
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String, String> params = new HashMap<>();
            params.put("idUser", nameUser);
            params.put("name", nameHouse);
            return params;
        }
    };
    RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);
        requestQueue.add(request);*/