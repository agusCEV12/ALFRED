package com.example.alfred.ui;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.alfred.R;

import java.util.ArrayList;
import java.util.List;

public class Gastos extends AppCompatActivity {

    String[] numbers = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "Jack", "Queen", "King"};
    ArrayList<item_gasto> QuestionForSliderMenu;

    private Gastos_adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gastos);

        ListView listView = (ListView) findViewById(R.id.lista_gastos);


        QuestionForSliderMenu = new ArrayList<>();
        QuestionForSliderMenu.add(new item_gasto("uno"));
        QuestionForSliderMenu.add(new item_gasto("dos"));
        QuestionForSliderMenu.add(new item_gasto("tres"));


        adapter = new Gastos_adapter (this, android.R.layout.simple_list_item_1, QuestionForSliderMenu);

        listView.setAdapter(adapter);
    }
}