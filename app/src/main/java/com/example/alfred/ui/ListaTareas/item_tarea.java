package com.example.alfred.ui.ListaTareas;

public class item_tarea {

    private String text;
    private boolean checked;

    public item_tarea(final String text, final boolean checked) {
        this.text = text;
        this.checked = checked;
    }

    public String getText() {
        return this.text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public boolean isChecked() {
        return this.checked;
    }

    public void setChecked(final boolean checked) {
        this.checked = checked;
    }
}
