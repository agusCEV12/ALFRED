package com.example.alfred.ui;

import static com.example.alfred.R.*;

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

public class Lista_compra extends AppCompatActivity {

    private ArrayList<item_compra> itemList;
    private Lista_compra_adapter adapter;

    private ListView lista;
    private Button btn_add;
    private EditText edit_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_lista_compra);

        lista = findViewById(id.list);
        btn_add = findViewById(id.btn_add);
        //edit_item = findViewById(id.edit_item);

        //Aqui habria que hacer que los elementos salgan de la BBDD "Supongo"
        itemList = new ArrayList<>();
        itemList.add(new item_compra("Patatas"));
        itemList.add(new item_compra("Papel"));

        adapter = new Lista_compra_adapter(this, android.R.layout.simple_list_item_1, itemList);

        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                item_compra item = itemList.get(pos);
                boolean checked = item.isChecked();
                itemList.get(pos).setChecked(!checked);
                adapter.notifyDataSetChanged();
            }
        });

        // Borramos el item de la lista que mantengamos presionado
        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> lista, View item, int pos, long id) {
                maybeRemoveItem(pos);
                return true;
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem();
            }
        });

        //Permite añadir un item a la lista desde el teclado
        edit_item.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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
        builder.setMessage(String.format("Seguro que quieres eliminar \'%1$s\' ?", itemList.get(pos).getText()));
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                itemList.remove(pos);
                adapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.create().show();
    }

    //Metodo para añadir un item a la lista y borrar el campo de texto
    private void addItem() {
        String item_text = edit_item.getText().toString();

        if (!item_text.isEmpty()){
            itemList.add(new item_compra(item_text));
            adapter.notifyDataSetChanged();
            edit_item.getText().clear();
        }
        lista.smoothScrollToPosition(itemList.size()-1);
    }
}