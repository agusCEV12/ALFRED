package com.example.alfred.ui.Espacios;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.alfred.R;
import com.example.alfred.ui.Gastos_bbdd.Gastos_bbdd_activity;
import com.example.alfred.ui.ListaTareas.Tareas_bbdd.Tareas_bbdd_activity;
import com.example.alfred.ui.Lista_compra.Prueba.prueba_lista_compra_activity;
import com.example.alfred.ui.login.Login_logo_activity;

import utils.PreferenceUtils;

public class profileActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    // DECLARACION DE VARIABLES
    ImageButton btn_back_profile;

    // Variables para el menu de navegacion lateral
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;

    ListView lista_menu_profile;

    @SuppressLint("WrongViewCast")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // VINCULACION DE VARIABLES
        btn_back_profile = findViewById(R.id.btn_back_profile);

        // localizamos el drawer menu, y lo mostramos
        drawerLayout = findViewById(R.id.main_layout_Profile);
        lista_menu_profile = findViewById(R.id.lista_menu_profile);   //Esto es el listView en si para poder reconocer el item
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.string.app_name,
                R.string.app_name
        );
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lista_menu_profile.setOnItemClickListener(this); //Para poder reconocer el item de la lista que estamos clickando


        // MÉTODO PARA EL BOTON DE "VOLVER"
        btn_back_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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
                Intent intent4 = new Intent(this, Tareas_bbdd_activity.class);
                startActivity(intent4);
                break;
            case 3:
                Intent intent2 = new Intent(this, Gastos_bbdd_activity.class);
                startActivity(intent2);
                break;
            case 4:
                recreate();
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
