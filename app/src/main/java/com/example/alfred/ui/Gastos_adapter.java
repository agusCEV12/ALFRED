package com.example.alfred.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;

import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;


import com.example.alfred.R;

import java.util.ArrayList;
import java.util.List;

public class Gastos_adapter extends ArrayAdapter<item_gasto> {
    Button btn_gastos_item;
    TextView textView;
    CheckBox checkBox;
    public Gastos_adapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context,resource,objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //
        View result = convertView;
        if (result == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            result = inflater.inflate(R.layout.item_gasto, null );
        }

        CheckBox checkBox = (CheckBox) result.findViewById(R.id.checkbox_item_gasto);
        TextView textView = (TextView) result.findViewById(R.id.textView_precio_item_gastos);
        Button button = result.findViewById(R.id.btn_item_gastos);
        item_gasto item_text = getItem(position);

        checkBox.setText(item_text.getText());
        textView.setText(item_text.getCoste());
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
                        remove(item_text);
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, null);
                builder.create().show();
            }
        });

        checkBox.setChecked(item_text.isChecked());

        return result;
    }




    /*
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //
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
