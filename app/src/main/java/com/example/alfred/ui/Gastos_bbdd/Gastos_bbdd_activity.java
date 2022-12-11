package com.example.alfred.ui.Gastos_bbdd;

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
import com.example.alfred.ui.Lista_compra.Prueba.Config5;
import com.example.alfred.ui.Lista_compra.Prueba.prueba_lista_compra_activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import utils.PreferenceUtils;

public class Gastos_bbdd_activity extends AppCompatActivity {

    //Declaración de variables
    EditText et_add_bill;
    Button btn_add_bill;
    ListView listview;
    String home;
    ProgressDialog mProgressDialog;

    String URL = "https://unscholarly-princip.000webhostapp.com/addBill.php";
    String URL2 = "https://unscholarly-princip.000webhostapp.com/deleteBill.php";

    public static final String KEY_HOME = "nameHome";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gastos_bbdd);

        //Asignación de variables
        et_add_bill = findViewById(R.id.et_add_bills);
        btn_add_bill = findViewById(R.id.btn_add_bills);
        listview = findViewById(R.id.lv_bills_bbdd);


        GetMatchData();

        btn_add_bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBills(et_add_bill.getText().toString());
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
                String[] finale = new String[ary.length -7];
                int x = 0;
                for (int i = 6; i < ary.length -1; i++){
                    while(x + 6 == i){
                        finale[x] = ary[i];
                        x++;
                    }
                }
                String bill = String.join("", finale);
                removeBills(pos, bill);
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

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config5.MATCHDATA_URL_BILS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            showJSON(response);
                            mProgressDialog.dismiss();

                        } else {
                            showJSON(response);
                            mProgressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Gastos_bbdd_activity.this, ""+error, Toast.LENGTH_LONG).show();
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
                this, list, R.layout.item_gastos_bbdd,
                new String[]{"article"},
                new int[]{R.id.tvbill});

        listview.setAdapter(adapter);

    }

    //------------------------------------------------------------------------------------------------------------------
    //METODO PARA AÑADIR ELEMENTOS A LA LISTA
    public void addBills(String bill){
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("Gasto añadido")){


                } else{
                    Log.d("Hola", "No compruebo nada");
                }
            }
        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Gastos_bbdd_activity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("nameHome",PreferenceUtils.getHome(Gastos_bbdd_activity.this));
                params.put("bill", bill);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }



    public void removeBills(Integer pos, String bill){

        StringRequest request = new StringRequest(Request.Method.POST, URL2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("Gasto eliminado")){
                    Toast.makeText(Gastos_bbdd_activity.this, "Eliminado",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(Gastos_bbdd_activity.this, "Error al eliminar", Toast.LENGTH_SHORT).show();
                }
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Gastos_bbdd_activity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("nameHome", PreferenceUtils.getHome(Gastos_bbdd_activity.this));
                params.put("bill", bill);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
    public void goToOptions (View view) {
        Intent intent = new Intent(this, SalaPrincipal.class);
        startActivity(intent);
        finish();
    }

}

