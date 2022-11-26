package com.example.alfred.ui.Lista_compra.Prueba;

public class prueba_item_compra {
    private String text;
    private boolean checked;

    public prueba_item_compra(String text) {
        this.text = text;
    }

    public prueba_item_compra(String text, boolean checked) {
        this.text = text;
        this.checked = checked;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
