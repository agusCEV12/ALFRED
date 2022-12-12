package com.example.alfred.ui.Lista_compra;

import static com.example.alfred.R.id;
import static com.example.alfred.R.layout;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.alfred.R;
import com.example.alfred.ui.Espacios.SalaPrincipal;
import com.example.alfred.ui.Gastos;
import com.example.alfred.ui.ListaTareas.TareasActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import utils.PreferenceUtils;

public class Lista_compra extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ArrayList<item_compra> itemList_compra;
    private Lista_compra_adapter adapter;
    String URL = "https://unscholarly-princip.000webhostapp.com/addArticles.php";
    String URL2 = "https://unscholarly-princip.000webhostapp.com/getHome.php";
    String nameHouse;

    private ListView lista_compra;
    private ImageButton btn_add_compra;

    // Variables para el menu de navegacion lateral
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;

    ListView lista_menu_compra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_lista_compra);

        lista_compra = findViewById(id.lista_compra);
        btn_add_compra = findViewById(id.btn_add_compra);
        getHome();

        //Recogemos el nombre de la Casa
        Bundle extra = getIntent().getExtras();
        if (extra != null){
            nameHouse = extra.getString("home");
        } else{
            nameHouse = PreferenceUtils.getEmail(this);
        }


        //Aqui habria que hacer que los elementos salgan de la BBDD "Supongo"
        itemList_compra = new ArrayList<>();

        // localizamos el drawer menu, y lo mostramos
        drawerLayout = findViewById(id.main_layout_Compra);
        lista_menu_compra = findViewById(R.id.lista_menu_compra);   //Esto es el listView en si para poder reconocer el item
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.string.app_name,
                R.string.app_name
        );
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lista_menu_compra.setOnItemClickListener(this); //Para poder reconocer el item de la lista que estamos clickando

        adapter = new Lista_compra_adapter(this, android.R.layout.simple_list_item_1, itemList_compra);

        lista_compra.setAdapter(adapter);

        lista_compra.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                item_compra item = itemList_compra.get(pos);
                boolean checked = item.isChecked();
                itemList_compra.get(pos).setChecked(!checked);
                adapter.notifyDataSetChanged();
            }
        });

        // Borramos el item de la lista que mantengamos presionado
        lista_compra.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> lista, View item, int pos, long id) {
                maybeRemoveItem(pos);
                Toast.makeText(Lista_compra.this,"Articulo eliminado", Toast.LENGTH_LONG).show();
                return true;
            }
        });

        btn_add_compra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View result = view;
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(layout.popup_invitacion, null);

                // Creamos la ventana del pop-up
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                // enseñamos la ventana del pop-up
                popupWindow.showAtLocation(result, Gravity.CENTER, 0, 0);

                @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button button = (Button) popupView.findViewById(R.id.btn_popup_agregarTarea_aceptar);
                @SuppressLint({"MissingInflatedId", "LocalSuppress"}) EditText nombre = popupView.findViewById(R.id.text_popup_AgregarTarea_nombre_tarea);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(Lista_compra.this, "Compra agregada correctamente", Toast.LENGTH_SHORT).show();
                        addItem(nombre.getText().toString());
                        Toast.makeText(Lista_compra.this, "Compra agregada", Toast.LENGTH_SHORT).show();
                        popupWindow.dismiss();
                    }
                });
            }
        });
    }

    //Metodo para que nos salga un pop-up para confirmar si queremos eliminar un elemento
    private void maybeRemoveItem ( int pos){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmación");
        builder.setMessage(String.format("Seguro que quieres eliminar \'%1$s\' ?", itemList_compra.get(pos).getText()));
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                itemList_compra.remove(pos);
                adapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.create().show();
    }

    //Metodo para añadir un item a la lista y borrar el campo de texto
    private void addItem (String compra){

        if (!compra.isEmpty()) {
            itemList_compra.add(new item_compra(compra));
            adapter.notifyDataSetChanged();
        }
        lista_compra.smoothScrollToPosition(itemList_compra.size() - 1);
    }

    private void setItem ( int position, String item_text){
        itemList_compra.set(position, new item_compra(item_text));
    }


    //Bloque de Metodos del Menu -------------------------------------------------------------------

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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i){
            case 0:
                Intent intent = new Intent(this, SalaPrincipal.class);
                startActivity(intent);
                break;
            case 1:
                recreate();
                break;
            case 2:
                Intent intent1 = new Intent(this, TareasActivity.class);
                startActivity(intent1);
                break;
            case 3:
                Intent intent2 = new Intent(this, Gastos.class);
                startActivity(intent2);
                break;
            default:
                break;
        }
    }

    public void addArticle (View view){

        EditText article = findViewById(R.id.text_popup_AgregarTarea_nombre_tarea);
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("Articulo añadido")){
                    article.setText("");

                }
                else{
                    Toast.makeText(Lista_compra.this,"UPS ERROR", Toast.LENGTH_LONG).show();
                    Log.d("el error es:", response);
                }
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Lista_compra.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("nameHome", PreferenceUtils.getHome(Lista_compra.this));
                params.put("articles", String.valueOf(article));
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Lista_compra.this);
        requestQueue.add(request);
    }
    // ---------------------------------------------------------------------------------------------


    public void getHome (){
        StringRequest request = new StringRequest(Request.Method.POST, URL2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response != null || !response.equals("")){

                PreferenceUtils.saveHome(response, Lista_compra.this);
                    Toast.makeText(Lista_compra.this, PreferenceUtils.getHome(Lista_compra.this),
                            Toast.LENGTH_SHORT).show();

                } else{
                    Toast.makeText(Lista_compra.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Lista_compra.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("email",PreferenceUtils.getEmail(Lista_compra.this));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}