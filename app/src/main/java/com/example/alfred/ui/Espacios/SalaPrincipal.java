package com.example.alfred.ui.Espacios;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.alfred.R;
import com.example.alfred.ui.Gastos;
import com.example.alfred.ui.ListaTareas.TareasActivity;
import com.example.alfred.ui.Lista_compra;

public class SalaPrincipal extends AppCompatActivity {

    Button btn_Lista_Compra;
    Button btn_Lista_Tareas;
    Button btn_Lista_Gastos;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones);

        btn_Lista_Compra = findViewById(R.id.btn_salas_compra);
        btn_Lista_Gastos = findViewById(R.id.btn_salas_gastos);
        btn_Lista_Tareas = findViewById(R.id.btn_salas_tarea);

    }


    public void goToTareas (View view){
        Intent intent = new Intent(this, TareasActivity.class);
        startActivity(intent);
    }
    public void goToCompras (View view){
        Intent intent = new Intent(this, Lista_compra.class);
        startActivity(intent);
    }
    public void goToGastos (View view){
        Intent intent = new Intent(this, Gastos.class);
        startActivity(intent);
    }
}
