package com.example.alfred.ui.recovery_pass;

import android.nfc.Tag;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alfred.R;

import java.util.Arrays;

public class RecoverPassActivity extends AppCompatActivity {

    Button baton;
    EditText introduceMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recover_passs_activity);

        baton = findViewById(R.id.recuperarMailBut);
        introduceMail = findViewById(R.id.textMail);

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
}