package com.example.alfred.ui.ListaTareas.Tareas_bbdd;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.alfred.R;
import com.example.alfred.ui.Espacios.SalaPrincipal;
import com.example.alfred.ui.Espacios.profileActivity;
import com.example.alfred.ui.Gastos_bbdd.Gastos_bbdd_activity;
import com.example.alfred.ui.Lista_compra.Prueba.Config5;
import com.example.alfred.ui.Lista_compra.Prueba.prueba_lista_compra_activity;
import com.example.alfred.ui.login.Login_logo_activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import utils.PreferenceUtils;

public class Tareas_bbdd_activity extends  AppCompatActivity implements AdapterView.OnItemClickListener{

        EditText et_add_task;
        Button btn_add_task;
        ListView listview;
        String home;
        ProgressDialog mProgressDialog;

    // Variables para el menu de navegacion lateral
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;

    ListView lista_menu_tareas;

        String URL = "https://unscholarly-princip.000webhostapp.com/addTask.php";
        String URL2 = "https://unscholarly-princip.000webhostapp.com/deleteTask.php";

        public static final String KEY_HOME = "nameHome";

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_tareas_bbdd);
            et_add_task = findViewById(R.id.et_add_task);
            btn_add_task = findViewById(R.id.btn_add_task);
            listview = findViewById(R.id.lv_tareas_bbdd);


            GetMatchData();

            btn_add_task.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addTasks(et_add_task.getText().toString());
                    finish();
                    startActivity(getIntent());
                }
            });

            // localizamos el drawer menu, y lo mostramos
            drawerLayout = findViewById(R.id.main_layout_Tareas);
            lista_menu_tareas = findViewById(R.id.lista_menu_tareas);   //Esto es el listView en si para poder reconocer el item
            actionBarDrawerToggle = new ActionBarDrawerToggle(
                    this,
                    drawerLayout,
                    R.string.app_name,
                    R.string.app_name
            );
            drawerLayout.addDrawerListener(actionBarDrawerToggle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            lista_menu_tareas.setOnItemClickListener(this); //Para poder reconocer el item de la lista que estamos clickando

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
                    String task = String.join("", finale);
                    Toast.makeText(Tareas_bbdd_activity.this, task, Toast.LENGTH_SHORT).show();
                    removeTasks(pos, task);
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

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Config5.MATCHDATA_URL_TASK,
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
                            Toast.makeText(Tareas_bbdd_activity.this, ""+error, Toast.LENGTH_LONG).show();
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
                    String task = jo.getString("task");

                    final HashMap<String, String> tasks = new HashMap<>();
                    tasks.put("task",  task);

                    list.add(tasks);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ListAdapter adapter = new SimpleAdapter(
                    this, list, R.layout.item_tareas_bbdd,
                    new String[]{"task"},
                    new int[]{R.id.tvtask});

            listview.setAdapter(adapter);

        }

        //------------------------------------------------------------------------------------------------------------------
        //METODO PARA AÑADIR ELEMENTOS A LA LISTA
        public void addTasks(String task){
            StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if(response.contains("Tarea Realizada")){


                    } else{
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
                    params.put("nameHome",PreferenceUtils.getHome(Tareas_bbdd_activity.this));
                    params.put("task", task);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(request);
        }



        public void removeTasks(Integer pos, String task){

            StringRequest request = new StringRequest(Request.Method.POST, URL2, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if(response.contains("Tarea Realizada")){
                        Toast.makeText(Tareas_bbdd_activity.this, "Eliminado",
                                Toast.LENGTH_SHORT).show();
                    }
                    else{
                    }
                }
            },new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(Tareas_bbdd_activity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    params.put("nameHome", PreferenceUtils.getHome(Tareas_bbdd_activity.this));
                    params.put("task", task);
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

    //Bloque de Metodos del Menu -------------------------------------------------------------------

    @Override
    public boolean onOptionsItemSelected(@NonNull final MenuItem item) {
        actionBarDrawerToggle.onOptionsItemSelected(item);
        return true;
    }

    @Override
    protected void onPostCreate(@Nullable final Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i){
            case 0:
                Intent intent = new Intent(this, SalaPrincipal.class);
                startActivity(intent);
                break;
            case 1:
                Intent intent1 = new Intent(this, prueba_lista_compra_activity.class);
                startActivity(intent1);
                break;
            case 2:
                recreate();
                break;
            case 3:
                Intent intent2 = new Intent(this, Gastos_bbdd_activity.class);
                startActivity(intent2);
                break;
            case 4:
                Intent intent4 = new Intent(this, profileActivity.class);
                startActivity(intent4);
                break;
            case 5: // ESTE ES EL CASO DE LA OPCIÓN DE CERRAR SESIÓN
                try {
                    if (PreferenceUtils.getEmail(this) != null || !PreferenceUtils.getEmail(this).equals("")){
                        PreferenceUtils.deleteSharedPre(this);
                        Intent intent3 = new Intent(this, Login_logo_activity.class);
                        startActivity(intent3);
                    } else{

                    }

                }catch (Exception a){
                    Toast.makeText(this, "Error en el Log Out", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
    // Cierre del Bloque de Metodos del Menu -------------------------------------------------------

}
