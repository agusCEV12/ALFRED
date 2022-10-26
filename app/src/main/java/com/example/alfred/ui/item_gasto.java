package com.example.alfred.ui;

public class item_gasto {
    private String text;
    private boolean checked;

    public item_gasto(String text) {
        this.text = text;
    }

    public item_gasto(String text, boolean checked) {
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
