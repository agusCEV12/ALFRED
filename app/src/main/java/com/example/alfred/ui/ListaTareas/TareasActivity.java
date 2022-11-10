package com.example.alfred.ui.ListaTareas;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alfred.R;
import com.example.alfred.ui.Gastos;
import com.example.alfred.ui.Lista_compra_adapter;
import com.example.alfred.ui.ToolbarActivity;
import com.example.alfred.ui.item_compra;
import com.example.alfred.ui.item_gasto;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class TareasActivity extends ToolbarActivity {

    private ArrayList<item_tarea> itemList_tareas;
    private Tareas_adapter adapter;

    private ListView lista_tareas;
    private Button btn_add_tareas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tareas);

        lista_tareas = findViewById(R.id.list_tareas);
        btn_add_tareas = findViewById(R.id.btn_add_tareas);

        //Aqui habria que hacer que los elementos salgan de la BBDD "Supongo"
        itemList_tareas = new ArrayList<>();
        itemList_tareas.add(new item_tarea("Recoger el salon"));
        itemList_tareas.add(new item_tarea("lavar los platos"));

        adapter = new Tareas_adapter(this, android.R.layout.simple_list_item_1, itemList_tareas);

        lista_tareas.setAdapter(adapter);

        lista_tareas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                item_tarea item = itemList_tareas.get(pos);
                boolean checked = item.isChecked();
                itemList_tareas.get(pos).setChecked(!checked);
                adapter.notifyDataSetChanged();
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

        btn_add_tareas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View result = view;
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.popup_add_tarea, null);

                // Creamos la ventana del pop-up
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                // enseñamos la ventana del pop-up
                popupWindow.showAtLocation(result, Gravity.CENTER, 0, 0);

                Button button = (Button) popupView.findViewById(R.id.btn_popup_agregarTarea_aceptar);
                EditText nombre = popupView.findViewById(R.id.text_popup_AgregarTarea_nombre_tarea);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(TareasActivity.this, "Tarea agregada correctamente", Toast.LENGTH_SHORT).show();
                        addItem(nombre.getText().toString());
                        popupWindow.dismiss();
                    }
                });
            }
        });
    }

        //Permite añadir un item a la lista desde el teclado
        /*edit_item_tareas.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                addItem();
                return true;
            }
        });
    }*/

        //Metodo para que nos salga un pop-up para confirmar si queremos eliminar un elemento
        private void maybeRemoveItem ( int pos){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Confirmación");
            builder.setMessage(String.format("Seguro que quieres eliminar \'%1$s\' ?", itemList_tareas.get(pos).getText()));
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    itemList_tareas.remove(pos);
                    adapter.notifyDataSetChanged();
                }
            });
            builder.setNegativeButton(android.R.string.cancel, null);
            builder.create().show();
        }

        //Metodo para añadir un item a la lista y borrar el campo de texto
        private void addItem (String tarea){

            if (!tarea.isEmpty()) {
                itemList_tareas.add(new item_tarea(tarea));
                adapter.notifyDataSetChanged();
            }
            lista_tareas.smoothScrollToPosition(itemList_tareas.size() - 1);
        }

        private void setItem ( int position, String item_text){
            itemList_tareas.set(position, new item_tarea(item_text));
        }
    }