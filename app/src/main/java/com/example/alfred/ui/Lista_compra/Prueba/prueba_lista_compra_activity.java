package com.example.alfred.ui.Lista_compra.Prueba;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.alfred.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class prueba_lista_compra_activity extends AppCompatActivity {

    EditText editTexthome;
    Button buttonfetch;
    ListView listview;
    String home;
    ProgressDialog mProgressDialog;

    public static final String KEY_HOME = "home";

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prueba_lista_compra);
        editTexthome = findViewById(R.id.etname);
        buttonfetch = findViewById(R.id.btnfetch);
        listview = findViewById(R.id.listView);
        buttonfetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                home = editTexthome.getText().toString();

                if (home.equals("")){
                    Toast.makeText(prueba_lista_compra_activity.this, "Please Enter a Home",
                            Toast.LENGTH_SHORT).show();
                }else {
                    GetMatchData();
                }
            }
        });
    }

    private void GetMatchData() {

        home = editTexthome.getText().toString();

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
                employees.put("article", "Articulo = " +article);

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

}
