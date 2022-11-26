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

import com.example.alfred.R;
import com.example.alfred.ui.Gastos;
import com.example.alfred.ui.ListaTareas.TareasActivity;
import com.example.alfred.ui.Lista_compra.Lista_compra;
import com.example.alfred.ui.Lista_compra.Prueba.prueba_lista_compra_activity;
import com.example.alfred.ui.login.LoginActivity;

import utils.PreferenceUtils;

public class SalaPrincipal extends AppCompatActivity implements AdapterView.OnItemClickListener {

    Button btn_Lista_Compra;
    Button btn_Lista_Tareas;
    Button btn_Lista_Gastos;

    Button buttonTarea3;

    TextView titulo_sala_principal;

    ListView lista_menu_sala;

    String sharedEmail;

    // Variables para el menu de navegacion lateral
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;

    @SuppressLint({"MissingInflatedId"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sala_principal);

        btn_Lista_Compra = findViewById(R.id.btn_salas_compra);
        btn_Lista_Gastos = findViewById(R.id.btn_salas_gastos);
        btn_Lista_Tareas = findViewById(R.id.btn_salas_tarea);
        lista_menu_sala = findViewById(R.id.lista_menu_sala);
        buttonTarea3 = findViewById(R.id.buttonTarea3);
        buttonTarea3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SalaPrincipal.this, prueba_lista_compra_activity.class);
                startActivity(intent);
            }
        });
        //titulo_sala_principal = findViewById(R.id.titulo_sala_principal);

        //titulo_sala_principal.setText(PreferenceUtils.getHome(this));

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

        comprobarSharedPrefs();

    }

    private void comprobarSharedPrefs() {
        if (PreferenceUtils.getEmail(this) != null || !PreferenceUtils.getEmail(this).equals("")){
            sharedEmail = PreferenceUtils.getEmail(this);
        } else {
            Toast.makeText(this, "Problema con el Shared Preference Email", Toast.LENGTH_LONG).show();
        }
    }

    // Metodo para ir a la actividad de Tareas
    public void goToTareas (View view){
        Intent intent = new Intent(this, TareasActivity.class);
        startActivity(intent);
    }

    // Metodo para ir a la actividad de Compras
    public void goToCompras (View view){
        Intent intent = new Intent(this, Lista_compra.class);
        startActivity(intent);
    }

    // Metodo para ir a la actividad de Gastos
    public void goToGastos (View view){
        Intent intent = new Intent(this, Gastos.class);
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


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        switch (i){
            case 0:
                recreate();
                break;
            case 1:
                Intent intent = new Intent(this, Lista_compra.class);
                startActivity(intent);
                break;
            case 2:
                Intent intent1 = new Intent(this, TareasActivity.class);
                startActivity(intent1);
                break;
            case 3:
                Intent intent2 = new Intent(this, Gastos.class);
                startActivity(intent2);
                break;
            case 4:
                Log.d("mi cuenta", "Hemos pulsado en boton mi cuenta del menu lateral en Sala principal");
                break;
            case 5:
                try {
                    if (PreferenceUtils.getEmail(this) != null || !PreferenceUtils.getEmail(this).equals("")){
                        PreferenceUtils.deleteSharedPre(this);
                        Toast.makeText(this, "Funciona el Log Out", Toast.LENGTH_SHORT).show();
                        Intent intent3 = new Intent(this, LoginActivity.class);
                        startActivity(intent3);
                    } else{
                        Toast.makeText(this, "Funciona el LogOut pero el mail es null", Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception a){
                    Toast.makeText(this, "Error en el Log Out", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}
