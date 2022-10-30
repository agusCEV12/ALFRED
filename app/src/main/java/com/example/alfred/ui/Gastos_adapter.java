package com.example.alfred.ui;

import android.content.Context;
import android.support.annotation.NonNull;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;


import com.example.alfred.R;

import java.util.ArrayList;
import java.util.List;

public class Gastos_adapter extends ArrayAdapter<item_gasto> {

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
            result = inflater.inflate(R.layout.item_compra, null );
        }

        CheckBox checkBox = (CheckBox) result.findViewById(R.id.item_compra);
        item_gasto item_text = getItem(position);
        checkBox.setText(item_text.getText());

        checkBox.setChecked(item_text.isChecked());

        return result;
    }

}
