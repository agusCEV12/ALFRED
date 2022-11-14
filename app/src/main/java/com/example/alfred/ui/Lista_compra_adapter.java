package com.example.alfred.ui;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import com.example.alfred.R;

import java.util.List;

public class Lista_compra_adapter extends ArrayAdapter<item_compra> {
    public Lista_compra_adapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
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
        item_compra item_text = getItem(position);
        checkBox.setText(item_text.getText());

        checkBox.setChecked(item_text.isChecked());

        return result;
    }
}
