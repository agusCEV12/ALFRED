package com.example.alfred.ui.Lista_compra.Prueba;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.example.alfred.ui.ListaTareas.item_tarea;

import java.util.ArrayList;
import java.util.List;

public class prueba_listaCompra_adapter extends ArrayAdapter<prueba_item_compra> {

    public prueba_listaCompra_adapter(@NonNull Context context, int resource, @NonNull List<prueba_item_compra> objects) {
        super(context, resource, objects);
    }


}
