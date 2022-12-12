package com.example.alfred.ui.Espacios;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.alfred.R;

public class profileActivity extends AppCompatActivity {

    // DECLARACION DE VARIABLES
    ImageButton btn_back_profile;

    @SuppressLint("WrongViewCast")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // VINCULACION DE VARIABLES
        btn_back_profile = findViewById(R.id.btn_back_profile);

        // MÉTODO PARA EL BOTON DE "VOLVER"
        btn_back_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
