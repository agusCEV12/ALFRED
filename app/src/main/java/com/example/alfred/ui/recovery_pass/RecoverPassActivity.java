package com.example.alfred.ui.recovery_pass;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alfred.R;
import com.example.alfred.ui.Gastos;
import com.example.alfred.ui.ListaTareas.TareasActivity;
import com.example.alfred.ui.Lista_compra;

import java.util.Arrays;

public class RecoverPassActivity extends AppCompatActivity {

    Button baton;
    EditText introduceMail;
    Button list;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recover_passs_activity);

        baton = findViewById(R.id.recuperarMailBut);
        introduceMail = findViewById(R.id.textMail);
        list = findViewById(R.id.btn_list);

        introduceMail.getText().clear();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void recuperateMail(View view) {
        String mail = introduceMail.getText().toString();
        String[] email = mail.split("");
        if (introduceMail.getText().length() > 0 && Arrays.stream(email).anyMatch("@"::equals)) {
            Toast.makeText(getApplication(), " Email enviado", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplication(), "Error al rellenar formulario", Toast.LENGTH_LONG).show();
        }
    }

    public void goToList(View view) {
        Intent intent = new Intent(this, Lista_compra.class);
        startActivity(intent);
    }

    public void goToGastos(View view) {
        Intent intent = new Intent(this, Gastos.class);
        startActivity(intent);
    }

    public void goToTareas (View view) {
        Intent intent = new Intent(this, TareasActivity.class);
        startActivity(intent);
    }

}