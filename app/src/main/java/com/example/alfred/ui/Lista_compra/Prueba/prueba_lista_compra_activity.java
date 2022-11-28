package com.example.alfred.ui.Lista_compra.Prueba;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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
import com.example.alfred.ui.Lista_compra.Lista_compra;
import com.example.alfred.ui.login.Login_logo_activity;
import com.example.alfred.ui.ui.home.HomeActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import utils.PreferenceUtils;

public class prueba_lista_compra_activity extends AppCompatActivity {

    EditText et_add_article;
    Button btn_add_article;
    ListView listview;
    String home;
    ProgressDialog mProgressDialog;


    String URL = "https://unscholarly-princip.000webhostapp.com/addArticles.php";
    String URL2 = "https://unscholarly-princip.000webhostapp.com/deleteArticle.php";

    public static final String KEY_HOME = "home";

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prueba_lista_compra);
        et_add_article = findViewById(R.id.et_add_article);
        btn_add_article = findViewById(R.id.btn_add_article);
        listview = findViewById(R.id.listView);


        GetMatchData();

        btn_add_article.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addArticles(et_add_article.getText().toString());
                finish();
                startActivity(getIntent());
            }
        });

        // Borramos el item de la lista que mantengamos presionado
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> lista, View item, int pos, long id) {

                Object jsonObj = listview.getItemAtPosition(pos);
                String jsonString = String.valueOf(jsonObj);
                String[] ary = jsonString.split("");
                String[] finale = new String[ary.length -10];
                int x = 0;
                for (int i = 9; i < ary.length -1; i++){
                    while(x + 9 == i){
                        finale[x] = ary[i];
                        x++;
                    }
                }
                String art = String.join("", finale);
                Toast.makeText(prueba_lista_compra_activity.this, art, Toast.LENGTH_SHORT).show();
                removeArticle(pos, art);
                finish();
                startActivity(getIntent());
                return true;
            }
        });
    }

    private void GetMatchData() {

        home = PreferenceUtils.getHome(this);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setMessage("Esperando");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setProgress(0);
        mProgressDialog.setProgressNumberFormat(null);
        mProgressDialog.setProgressPercentFormat(null);
        mProgressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config5.MATCHDATA_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            Toast.makeText(prueba_lista_compra_activity.this, response, Toast.LENGTH_SHORT).show();
                            showJSON(response);
                            mProgressDialog.dismiss();

                        } else {
                            Toast.makeText(prueba_lista_compra_activity.this, "me muero", Toast.LENGTH_SHORT).show();
                            showJSON(response);
                            mProgressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(prueba_lista_compra_activity.this, ""+error, Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put(KEY_HOME, home);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response) {
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("result");

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String article = jo.getString("article");

                final HashMap<String, String> employees = new HashMap<>();
                employees.put("article",  article);

                list.add(employees);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ListAdapter adapter = new SimpleAdapter(
                this, list, R.layout.prueba_lista_compra_item,
                new String[]{"article"},
                new int[]{R.id.tvarticle});

        listview.setAdapter(adapter);

    }

    //------------------------------------------------------------------------------------------------------------------
    //METODO PARA AÑADIR ELEMENTOS A LA LISTA
    public void addArticles(String article){
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("This user already has a home")){


                }else if (response.contains("This user doesnt has a home")) {

                }
                else{
                    Log.d("Hola", "No compruebo nada");
                }
            }
        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(Login_logo_activity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("nameHome",PreferenceUtils.getHome(prueba_lista_compra_activity.this));
                params.put("articles", article);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }



    public void removeArticle(Integer pos, String art){

        StringRequest request = new StringRequest(Request.Method.POST, URL2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("Casa Creada")){
                    Toast.makeText(prueba_lista_compra_activity.this, "Eliminado",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                }
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(prueba_lista_compra_activity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                /*String[] ary = jsonString.split("");
                String[] finale = new String[ary.length - 9];
                int x = 0;
                for (int i = 9; i < ary.length; i++){
                    while(x + 9 == i){
                        finale[x] = ary[i];
                        x++;
                    }
                }
                String art = finale.toString();*/
                params.put("nameHome", PreferenceUtils.getHome(prueba_lista_compra_activity.this));
                params.put("articles", art);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(prueba_lista_compra_activity.this);
        requestQueue.add(request);
    }

}
