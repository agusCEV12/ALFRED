package com.example.alfred.ui.ListaTareas;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.alfred.R;
import com.example.alfred.ui.Lista_compra_adapter;
import com.example.alfred.ui.ToolbarActivity;
import com.example.alfred.ui.item_compra;

import java.util.ArrayList;

public class TareasActivity extends ToolbarActivity {

    //Declaracion de variables
    private ArrayList<item_tarea> itemTareas;
    //private TareasActivity_adapter adapter;

    private ListView lista_tareas;
    private Button btn_gregar_tarea;;
    TextView titulo_Tareas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tareas);

        lista_tareas = findViewById(R.id.lista_tareas_activity);
        btn_gregar_tarea = findViewById(R.id.btn_agregar_tarea);
        titulo_Tareas = findViewById(R.id.titulo_tareas);

        //Aqui habria que hacer que los elementos salgan de la BBDD "Supongo"
        itemTareas = new ArrayList<>();
        //itemTareas.add(new item_tarea("Patatas"));
        //itemTareas.add(new item_tarea("Papel"));

        //adapter = new TareasActivity_adapter(this, android.R.layout.simple_list_item_1, itemTareas);

        //lista_tareas.setAdapter(adapter);

        lista_tareas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                item_tarea item = itemTareas.get(pos);
                boolean checked = item.isChecked();
                itemTareas.get(pos).setChecked(!checked);
                //adapter.notifyDataSetChanged();
            }
        });

        // Borramos el item de la lista que mantengamos presionado
        lista_tareas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> lista, View item, int pos, long id) {
                maybeRemoveItem(pos);
                return true;
            }
        });

        /*btn_gregar_tarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem();
            }
        });*/

        //Permite añadir un item a la lista desde el teclado
        titulo_Tareas.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                //addItem();
                return true;
            }
        });
    }

    //Metodo para que nos salga un pop-up para confirmar si queremos eliminar un elemento
    private void maybeRemoveItem(int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmación");
        builder.setMessage(String.format("Seguro que quieres eliminar \'%1$s\' ?", itemTareas.get(pos).getText()));
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                itemTareas.remove(pos);
                //adapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.create().show();
    }

    //Metodo para añadir un item a la lista y borrar el campo de texto
    /*private void addItem() {
        String item_text = Titulo.getText().toString();

        if (!item_text.isEmpty()){
            itemList.add(new item_compra(item_text));
            adapter.notifyDataSetChanged();
            edit_item.getText().clear();
        }
        lista.smoothScrollToPosition(itemList.size()-1);
    }*/
}