package com.example.alfred.ui;

import static com.example.alfred.R.*;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.alfred.R;

import java.util.ArrayList;

public class Gastos extends AppCompatActivity {

    private ArrayList<item_gasto> itemList_gastos;
    private Gastos_adapter adapter_gastos;

    private ListView lista_gastos;
    private Button btn_add_gastos;
    private EditText edit_item_gastos;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_gastos);

        lista_gastos = findViewById(R.id.list_gastos);
        btn_add_gastos = findViewById(R.id.btn_add_gastos);
        edit_item_gastos = findViewById(R.id.edit_item_gastos);

        //Aqui habria que hacer que los elementos salgan de la BBDD "Supongo"
        itemList_gastos = new ArrayList<>();
        itemList_gastos.add(new item_gasto("Patatas"));
        itemList_gastos.add(new item_gasto("Papel"));

        adapter_gastos = new Gastos_adapter(this, android.R.layout.simple_list_item_1, itemList_gastos);

        lista_gastos.setAdapter(adapter_gastos);

        lista_gastos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                item_gasto item = itemList_gastos.get(pos);
                boolean checked = item.isChecked();
                itemList_gastos.get(pos).setChecked(!checked);
                adapter_gastos.notifyDataSetChanged();
            }
        });

        // Borramos el item de la lista que mantengamos presionado
        lista_gastos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> lista, View item, int pos, long id) {
                maybeRemoveItem(pos);
                return true;
            }
        });

        btn_add_gastos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem();
            }
        });

        //Permite añadir un item a la lista desde el teclado
        edit_item_gastos.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                addItem();
                return true;
            }
        });
    }

    //Metodo para que nos salga un pop-up para confirmar si queremos eliminar un elemento
    private void maybeRemoveItem(int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmación");
        builder.setMessage(String.format("Seguro que quieres eliminar \'%1$s\' ?", itemList_gastos.get(pos).getText()));
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                itemList_gastos.remove(pos);
                adapter_gastos.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.create().show();
    }

    //Metodo para añadir un item a la lista y borrar el campo de texto
    private void addItem() {
        String item_text = edit_item_gastos.getText().toString();

        if (!item_text.isEmpty()){
            itemList_gastos.add(new item_gasto(item_text));
            adapter_gastos.notifyDataSetChanged();
            edit_item_gastos.getText().clear();
        }
        lista_gastos.smoothScrollToPosition(itemList_gastos.size()-1);
    }
}