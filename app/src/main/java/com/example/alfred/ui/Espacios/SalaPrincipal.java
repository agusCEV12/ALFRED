package com.example.alfred.ui.Espacios;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.alfred.R;
import com.example.alfred.ui.Gastos;
import com.example.alfred.ui.ListaTareas.TareasActivity;
import com.example.alfred.ui.Lista_compra;

public class SalaPrincipal extends AppCompatActivity {

    Button btn_Lista_Compra;
    Button btn_Lista_Tareas;
    Button btn_Lista_Gastos;

    // Variables para el menu de navegacion lateral
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones);

        btn_Lista_Compra = findViewById(R.id.btn_salas_compra);
        btn_Lista_Gastos = findViewById(R.id.btn_salas_gastos);
        btn_Lista_Tareas = findViewById(R.id.btn_salas_tarea);

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

    }

    // Metodo para ir a la actividad de Taeas
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
}
