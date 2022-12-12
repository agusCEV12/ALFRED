package com.example.alfred.ui.Gastos_bbdd;

// CADA ITEM DE LA LISTA DE GASTOS
public class item_gastos_bbdd {

    private String text;
    public item_gastos_bbdd(String text) {
        this.text = text;
    }

    public item_gastos_bbdd(String text, boolean checked) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
