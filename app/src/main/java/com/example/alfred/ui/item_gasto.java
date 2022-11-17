package com.example.alfred.ui;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

public class item_gasto {
    private String nombre;
    // CheckBox checkBox;
    private String coste;
    ImageButton btn_item_gastos;

    public item_gasto(String text, String coste) {
        this.nombre = text;
        this.coste = coste;
    }

    public item_gasto(String text, boolean checked, String coste) {
        this.nombre = text;
        this.coste = coste;
    }

    public String getText() {
        return nombre;
    }

    public String getCoste(){
        return coste;
    }

    public void setText(String text) {
        this.nombre = text;
    }

    public void setCoste(String coste){
        this.coste = coste;
    }

    /*
    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    } */

    /* public void pulsado() {
        Toast.makeText(btn_item_gastos.getContext(), "Boton de la lista pulsado", Toast.LENGTH_SHORT).show();
    } */
}
