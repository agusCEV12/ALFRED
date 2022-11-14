package com.example.alfred.ui.login;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.example.alfred.R;

public class EspacioActivity extends AppCompatActivity {

    ImageButton imageButton;
    ImageButton imageButton2;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espacio);

        imageButton = findViewById(R.id.img_btn_espacio);
        imageButton2 = findViewById(R.id.btn_Back);

        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EspacioActivity.this.finish();
            }
        });
    }
}
