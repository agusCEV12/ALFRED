package com.example.alfred.ui.Espacios;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
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
import com.example.alfred.ui.Gastos;
import com.example.alfred.ui.Gastos_bbdd.Gastos_bbdd_activity;
import com.example.alfred.ui.ListaTareas.TareasActivity;
import com.example.alfred.ui.ListaTareas.Tareas_bbdd.Tareas_bbdd_activity;
import com.example.alfred.ui.Lista_compra.Lista_compra;
import com.example.alfred.ui.Lista_compra.Prueba.prueba_lista_compra_activity;
import com.example.alfred.ui.login.LoginActivity;
import com.example.alfred.ui.login.Login_logo_activity;

import java.util.HashMap;
import java.util.Map;

import utils.PreferenceUtils;

public class SalaPrincipal extends AppCompatActivity implements AdapterView.OnItemClickListener {

    // DECLARACIÓN DE VARIABLES
    Button btn_Lista_Compra;
    Button btn_Lista_Tareas;
    Button btn_Lista_Gastos;

    Button buttonTarea3;
    Button buttonCocina;
    Button buttonbanio;
    Button buttonOtros;

    TextView titulo_sala_principal;

    ListView lista_menu_sala;

    String sharedEmail;

    // Variables para el menu de navegacion lateral
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;

    String URL2 = "https://unscholarly-princip.000webhostapp.com/getHome.php";


    @SuppressLint({"MissingInflatedId"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sala_principal);

        btn_Lista_Compra = findViewById(R.id.btn_salas_compra);
        btn_Lista_Gastos = findViewById(R.id.btn_salas_gastos);
        btn_Lista_Tareas = findViewById(R.id.btn_salas_tarea);
        lista_menu_sala = findViewById(R.id.lista_menu_sala);

        buttonCocina = findViewById(R.id.buttonCocina);
        buttonbanio = findViewById(R.id.buttonbanio);
        buttonOtros = findViewById(R.id.buttonOtros);


        getHome();

        // localizamos el drawer menu, y lo mostramos
        drawerLayout = findViewById(R.id.main_layout_Sala);
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.string.app_name,
                R.string.app_name
        );
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lista_menu_sala.setOnItemClickListener(this);

        // METODO QUE COMPRUEBA LA SHARED PREFERENCE PARA SABER SI TENEMOS CREDENCIALES GUARDADAS
        comprobarSharedPrefs();

    }

    // COMPRUEBA SI TENEMOS UN USUARIO GUARDADO
    private void comprobarSharedPrefs() {
        if (PreferenceUtils.getEmail(this) != null || !PreferenceUtils.getEmail(this).equals("")){
            sharedEmail = PreferenceUtils.getEmail(this);
        } else {
        }
    }

    // Metodo para ir a la actividad de Tareas
    public void goToTareas (View view){
        Intent intent = new Intent(this, Tareas_bbdd_activity.class);
        startActivity(intent);
    }


    // Metodo para ir a la actividad de Compras
    public void goToCompras (View view){
        Intent intent = new Intent(this, prueba_lista_compra_activity.class);
        startActivity(intent);
    }

    // Metodo para ir a la actividad de Gastos
    public void goToGastos (View view){
        Intent intent = new Intent(this, Gastos_bbdd_activity.class);
        startActivity(intent);
    }

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


    // SWITCH DEL MENÚ LATERAL, QUE NOS LLEVA A LAS DIFERENTES PANTALLAS
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        switch (i){
            case 0:
                recreate();
                break;
            case 1:
                Intent intent = new Intent(this, prueba_lista_compra_activity.class);
                startActivity(intent);
                break;
            case 2:
                Intent intent1 = new Intent(this, Tareas_bbdd_activity.class);
                startActivity(intent1);
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

    // NOS CONECTAMOS CON LA BASE DE DATOS PARA OPTENER LA CASA ASOCIADA AL EMAIL EN CASO DE HABERLA
    public void getHome (){
        StringRequest request = new StringRequest(Request.Method.POST, URL2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response != null || !response.equals("")){

                    PreferenceUtils.saveHome(response, SalaPrincipal.this);
                    Toast.makeText(SalaPrincipal.this,
                            PreferenceUtils.getHome(SalaPrincipal.this),
                            Toast.LENGTH_SHORT).show();

                } else{
                    Toast.makeText(SalaPrincipal.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SalaPrincipal.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("email",PreferenceUtils.getEmail(SalaPrincipal.this));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}
