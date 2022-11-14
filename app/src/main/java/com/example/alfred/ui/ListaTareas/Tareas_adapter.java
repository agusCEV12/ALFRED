package com.example.alfred.ui.ListaTareas;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import com.example.alfred.R;

import java.util.List;

public class Tareas_adapter extends ArrayAdapter<item_tarea> {


    public Tareas_adapter(@NonNull Context context, int resource, @NonNull List<item_tarea> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull final ViewGroup parent) {

        // Inflamos la la vista de cada item de la lista
        View result = convertView;
        if (result == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            result = inflater.inflate(R.layout.item_tarea, null);
        }

        // Llamamos a los objetos que usaremos del xml de Tareas
        CheckBox checkBox = result.findViewById(R.id.checkbox_item_tarea);

        // Creamos un item tarea y usamos los metodos de item_tarea
        item_tarea item_text = getItem(position);
        checkBox.setText(item_text.getText());
        checkBox.setChecked(item_text.isChecked());

        if (checkBox.isChecked() == true){
            result.setBackgroundColor(Color.GRAY);
        } else {
            result.setBackgroundColor(Color.WHITE);
        }

        return result;
    }
}
