package com.example.alfred.ui;

import android.content.Context;
import android.content.DialogInterface;
import androidx.annotation.NonNull;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.example.alfred.R;

import java.util.ArrayList;
import java.util.List;

public class Gastos_adapter extends ArrayAdapter<item_gasto> {
    Button btn_gastos_item;
    TextView textView;
    TextView textView_gastos_item;
    TextView tex_2_item_gastos;
    String costeTotal;
    private ArrayList<item_gasto> itemList_gastos;
    public Gastos_adapter(@NonNull Context context, int resource, @NonNull List objects, TextView text_2_item_gastos) {
        super(context,resource,objects);
        this.tex_2_item_gastos = text_2_item_gastos;
    }


    //Metodo Get View generico
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //
        View result = convertView;
        if (result == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            result = inflater.inflate(R.layout.item_gasto, null );
        }

        TextView textView = (TextView) result.findViewById(R.id.textView_precio_item_gastos);
        TextView nombre = (TextView) result.findViewById(R.id.textview_item_gasto_nombre);
        ImageButton button = result.findViewById(R.id.btn_item_gastos);
        item_gasto item_text = getItem(position);

        nombre.setText(item_text.getText());
        textView.setText(item_text.getCoste());
        //metodo para eliminar el item al darle a "yes" en el cuadro de dialogo
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(button.getContext(), "Boton de la lista pulsado", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(button.getContext());
                builder.setTitle("Confirmación");
                builder.setMessage(String.format("Seguro que quieres eliminar \'%1$s\' ?", item_text.getText()));
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //Este bloque recibe el Coste total de la lista y le resta el coste del item seleccionado
                        int coste_actual = Integer.parseInt(item_text.getCoste());
                        int Coste_total = Integer.parseInt(tex_2_item_gastos.getText().toString());
                        Coste_total -= coste_actual;
                        ((Gastos) getContext()).setCoste(Integer.toString(Coste_total));

                        //Eliminar el item de la lista
                        remove(item_text);
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, null);
                builder.create().show();
            }
        });


        return result;
    }


    /*
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View result = convertView;
        if (result == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            result = inflater.inflate(R.layout.item_compra, null );
        }

        btn_gastos_item = (Button) result.findViewById(R.id.btn_item_gastos);
        textView = (TextView) result.findViewById(R.id.textView_item_gastos);
        checkBox = (CheckBox) result.findViewById(R.id.checkbox_item_gasto);


        try {
            textView.setText("List Item" + " : "+ position);
            btn_gastos_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    switch (view.getId()){
                        case R.id.btn_item_gastos:

                            PopupMenu popup = new PopupMenu(view.getContext(), view);
                            popup.getMenuInflater().inflate(R.menu.menu_pop_up, popup.getMenu());
                            popup.show();
                            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem menuItem) {

                                    switch (menuItem.getItemId()){
                                        case R.id.modificar:
                                            Toast.makeText(view.getContext(), "Modificar", Toast.LENGTH_LONG).show();
                                            break;
                                        case R.id.borrar:
                                            Toast.makeText(view.getContext(), "Borrar", Toast.LENGTH_LONG).show();
                                            break;
                                        default:
                                            break;
                                    }
                                    return true;
                                }
                            });
                            break;
                        default:
                            break;
                    }
                }
            });
        } catch (Exception e){
            e.printStackTrace();
        }

        item_gasto item_text = getItem(position);
        checkBox.setText(item_text.getText());


        checkBox.setChecked(item_text.isChecked());
        return result;
    } */

}
