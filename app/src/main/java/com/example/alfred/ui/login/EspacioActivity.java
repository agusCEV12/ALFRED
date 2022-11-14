package com.example.alfred.ui.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.example.alfred.R;
import com.example.alfred.ui.Espacios.EspaciosRegistered;
import com.example.alfred.ui.Espacios.SalaPrincipal;
import com.example.alfred.ui.recovery_pass.RecoverPassActivity;

public class EspacioActivity extends AppCompatActivity {

    ImageButton imageButton;
    ImageButton imageButton2;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espacio);

        drawerLayout = findViewById(R.id.main_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.string.app_name,
                R.string.app_name
        );
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageButton = findViewById(R.id.img_btn_espacio);
        imageButton2 = findViewById(R.id.btn_Back);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SalaPrincipal.class);
                startActivity(intent);
            }
        });
        /*imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EspacioActivity.this.finish();
            }
        });*/
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull final MenuItem item) {
        actionBarDrawerToggle.onOptionsItemSelected(item);
        return true;
    }

    @Override
    protected void onPostCreate(@Nullable final Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }
}
