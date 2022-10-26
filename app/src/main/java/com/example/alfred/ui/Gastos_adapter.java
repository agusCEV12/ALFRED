package com.example.alfred.ui;


import android.content.Context;
import android.support.annotation.NonNull;

import android.widget.ArrayAdapter;

import java.util.List;

public class Gastos_adapter extends ArrayAdapter<item_gasto> {

    public Gastos_adapter(@NonNull Context context, int resource, @NonNull List<item_gasto> objects) {
        super(context, resource, objects);
    }


}
