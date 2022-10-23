package com.example.alfred.ui.recovery_pass;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alfred.R;

public class RecoverPasssActivity extends AppCompatActivity {

    Button baton;
    EditText introduceMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recover_passs_activity);

        baton = findViewById(R.id.recuperarMailBut);
        introduceMail = findViewById(R.id.textMail);
    }

    public void recuperateMail(View view) {
        if (introduceMail.toString().length()>0){
            Toast.makeText(getApplication(), "Mail Enviado", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplication(), "Mail no Valido", Toast.LENGTH_LONG).show();
        }
    }

    /*public boolean comprobarMail (){

        boolean retorno = false;
        String[] email = introducirMail.toString().split("");
        for (int i = 0; i<= introducirMail.length(); i++){
            if (email[i] == "@"){
                return true;
            }
        }
        return false;
    }*/
}