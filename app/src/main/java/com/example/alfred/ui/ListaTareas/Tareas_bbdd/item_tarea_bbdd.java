package com.example.alfred.ui.ListaTareas.Tareas_bbdd;

public class item_tarea_bbdd {
    private String text;

    public item_tarea_bbdd(String text) {
        this.text = text;
    }

    public item_tarea_bbdd(String text, boolean checked) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
