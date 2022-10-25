package com.example.alfred.ui;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.alfred.R;

import java.util.ArrayList;
import java.util.List;

public class Gastos extends AppCompatActivity {

    private ArrayList<String> item_gastos;
    private ArrayAdapter<String> adapter;

    private TextView text_gastos;
    private Button btn_gastos;
    private ListView lista_gastos;

    //Pop-up para añadir elemento a la lista de gastos -> Nombre, gasto es dinero, etc...
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private EditText pop_up_nombre, pop_up_gasto;
    private Button pop_up_cancelar, pop_up_guardar;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gastos);

        text_gastos = findViewById(R.id.text_gastos);
        btn_gastos = findViewById(R.id.btn_gastos);
        btn_gastos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewContactDialog();
            }
        });
        lista_gastos = findViewById(R.id.lista_gastos);

        //Metemos un par de gastos para hacer la prueba
        item_gastos = new ArrayList<>();
        item_gastos.add("Factura de la Luz");
        item_gastos.add("Factura del Gas");
        item_gastos.add("Factura de la Compra Semanal");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, item_gastos);
        lista_gastos.setAdapter(adapter);
        
        
        //Borramos el item de la lista que mantengamos presionado
        lista_gastos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long id) {
                maybeRemoveItem(pos);
                return true;
            }
        });
    }

    private void maybeRemoveItem(int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmación");
        //Para ver el nombre del elemento que queremos eliminar
        builder.setMessage(String.format("Seguro que quieres eliminar \'%1$s\' ?", item_gastos.get(pos)));
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                item_gastos.remove(pos);
                adapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.create().show();
    }

    public void createNewContactDialog(){
        builder = new AlertDialog.Builder(this);
        final View contactPopupView = getLayoutInflater().inflate(R.layout.popup, null);

        pop_up_nombre = findViewById(R.id.text_nombre_gasto);
        pop_up_gasto = findViewById(R.id.text_cantidad);

        pop_up_cancelar =  findViewById(R.id.btn_popup_cancelar);
        pop_up_guardar = findViewById(R.id.btn_popup_aceptar);

        builder.setView(contactPopupView);
        dialog = builder.create();
        dialog.show();

    }
}