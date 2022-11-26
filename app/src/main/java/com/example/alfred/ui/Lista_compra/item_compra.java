package com.example.alfred.ui.Lista_compra;

public class item_compra {

    private String text;
    private boolean checked;

    public item_compra(String text) {
        this.text = text;
    }

    public item_compra(String text, boolean checked) {
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
