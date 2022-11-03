package com.example.alfred.ui;

import static com.example.alfred.R.*;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alfred.R;

import java.util.ArrayList;

public class Gastos extends ToolbarActivity {

    private ArrayList<item_gasto> itemList_gastos;
    private Gastos_adapter adapter_gastos;
    private Gastos_adapter adapter_popup;

    private ListView lista_gastos;
    private Button btn_add_gastos;
    private TextView text_item_gastos;
    private TextView text_2_item_gastos;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_gastos);

        lista_gastos = findViewById(R.id.list_gastos);
        btn_add_gastos = findViewById(R.id.btn_add_gastos);
        text_item_gastos = findViewById(R.id.edit_item_gastos);
        text_2_item_gastos = findViewById(R.id.edit_2_item_gastos);

        //Aqui habria que hacer que los elementos salgan de la BBDD "Supongo"
        itemList_gastos = new ArrayList<>();
        itemList_gastos.add(new item_gasto("Compra", "55"));
        itemList_gastos.add(new item_gasto("Clases Padel","80"));

        adapter_gastos = new Gastos_adapter(this, android.R.layout.simple_list_item_1, itemList_gastos, text_2_item_gastos);

        lista_gastos.setAdapter(adapter_gastos);

        text_2_item_gastos.setText(sumatorioLista(itemList_gastos));

        /*
        lista_gastos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                item_gasto item = itemList_gastos.get(pos);
                boolean checked = item.isChecked();
                itemList_gastos.get(pos).setChecked(!checked);
                adapter_gastos.notifyDataSetChanged();
            }
        });
         */
//-----------------------------------------------------------------------------------------------------------------------------
        // Modificamos el contenido de una fila tras mantener pulsado un elemento en conteto
        lista_gastos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> lista, View item, int pos, long id) {
                // inflate the layout of the popup window
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(layout.popup, null);

                // Creamos la ventana del pop-up
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                // enseñamos la ventana del pop-up
                popupWindow.showAtLocation(item, Gravity.CENTER, 0, 0);
                
                Button button = (Button) popupView.findViewById(R.id.btn_popup_aceptar);
                EditText cantidad = popupView.findViewById(R.id.text_cantidad);
                EditText nombre = popupView.findViewById(R.id.text_nombre_gasto);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(Gastos.this, "Datos Modificados correctamente", Toast.LENGTH_SHORT).show();
                        setItem(pos, nombre.getText().toString(),cantidad.getText().toString());
                        popupWindow.dismiss();
                    }
                });
                
                // dismiss the popup window when touched
                popupView.setOnTouchListener(new View.OnTouchListener() {
                    public boolean onTouch(View v, MotionEvent event) {
                        popupWindow.dismiss();
                        return true;
                    }
                });
                return true;
            }
        });

//-----------------------------------------------------------------------------------------------------------------------------

        /* METODO ANTIGUO PARA AÑADIR UN ELEMENTO DESDE UN ANTIGUO CAMPO DE TEXTO INFERIOR
        btn_add_gastos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem(edit_item_gastos.getText().toString(),edit_2_item_gastos.getText().toString() );
            }
        }); */

//-----------------------------------------------------------------------------------------------------------------------------

        btn_add_gastos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View result = view;
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                result = inflater.inflate(layout.popup_agregar_gastos, null);
                Toast.makeText(Gastos.this, "El boton funciona wachos", Toast.LENGTH_SHORT).show();

                // Creamos la ventana del pop-up
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // Esto nos permite que si clickamos fuera de la ventana del pop-up, esta desaparece
                final PopupWindow popupWindow = new PopupWindow(result, width, height, focusable);

                // enseñamos la ventana del pop-up
                popupWindow.showAtLocation(result,Gravity.CENTER, 0, 0);

                //Nos traemos los elementos de la ventana del pop up para utilizarlos aqui

                EditText nombre = result.findViewById(id.text_popup_AgregarGasto_nombre_gasto);
                EditText cantidad = result.findViewById(id.text_popup_AgregarGasto_cantidad);
                Button btn_agregar_Coste = result.findViewById(id.btn_popup_agregarGasto_aceptar);

                btn_agregar_Coste.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        addItem(nombre.getText().toString(), cantidad.getText().toString());
                        Toast.makeText(Gastos.this, "Gasto añadido correctamente", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        popupWindow.dismiss();
                    }
                });
            }
        });
    }

//-----------------------------------------------------------------------------------------------------------------------------

    //Metodo para que nos salga un pop-up para confirmar si queremos eliminar un elemento -> DESCACTUALIZADO <- SOLO INFORMATIVO
    /*private void maybeRemoveItem(int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmación");
        builder.setMessage(String.format("Seguro que quieres eliminar \'%1$s\' ?", itemList_gastos.get(pos).getText()));
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //text_2_item_gastos.setText(restaLista(itemList_gastos, pos));
                Toast.makeText(Gastos.this, "Holi, el gasto es: " + restaLista(itemList_gastos, pos), Toast.LENGTH_SHORT).show();
                //itemList_gastos.remove(pos);
                //adapter_gastos.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.create().show();
    } */

    //Metodo para añadir un item a la lista y borrar el campo de texto
    private void addItem(String item_text, String item_text_coste ) {

        if (!item_text.isEmpty()){
            itemList_gastos.add(new item_gasto(item_text, item_text_coste));
            adapter_gastos.notifyDataSetChanged();
        }
        text_2_item_gastos.setText(sumatorioLista(itemList_gastos));
        lista_gastos.smoothScrollToPosition(itemList_gastos.size()-1);
    }

    private void setItem (int position, String item_text, String item_text_coste) {
        itemList_gastos.set(position, new item_gasto(item_text, item_text_coste));
    }

//-----------------------------------------------------------------------------------------------------------------------------

    //Suma el coste de los elementos de la Lista
    public String sumatorioLista (ArrayList<item_gasto> arrayList) {
        int item_gastoss = 0;

        for (int i = 0; i < arrayList.size(); i++){
            item_gasto item_text = arrayList.get(i);
            int coste = Integer.parseInt(item_text.getCoste());
            item_gastoss += coste;
        }
        return Integer.toString(item_gastoss);
    }

    //Resta el coste cuando elementos de la lista son eliminados
    public String restaLista (ArrayList<item_gasto> arrayList, int posicion){
        int item_gastoss = Integer.parseInt(text_2_item_gastos.getText().toString());

        item_gasto item_text_p = arrayList.get(posicion);
        int coste_p = Integer.parseInt(item_text_p.getCoste());

        item_gastoss -= coste_p;
        itemList_gastos.remove(posicion);
        adapter_gastos.notifyDataSetChanged();

        return text_2_item_gastos.getText().toString();
    }

    public String getCoste(){
        return text_2_item_gastos.getText().toString();
    }

    public void setCoste (String coste){
        text_2_item_gastos.setText(coste);
    }

}