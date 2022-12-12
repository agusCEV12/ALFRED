package com.example.alfred.ui.Lista_compra.Prueba;


// CADA ITEM DE LA LISTA DE PRODUCTOS DE LA COMPRA
public class prueba_item_compra {
    private String text;

    public prueba_item_compra(String text) {
        this.text = text;
    }

    public prueba_item_compra(String text, boolean checked) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
