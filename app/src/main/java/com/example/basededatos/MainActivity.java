package com.example.basededatos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    EditText txt_nou_nom;
    EditText txt_nou_txt;
    Button btn_add;

    TextView txt_veure_nom;
    TextView txt_veure_com;
    Button btn_veure;

    Spinner spn_com;

    Button btn_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataBase db = new DataBase(this);
        spn_com = (Spinner) findViewById(R.id.spn_sel_com);

        txt_nou_nom = (EditText) findViewById(R.id.nou_nom_com);
        txt_nou_txt = (EditText) findViewById(R.id.nou_txt_com);
        btn_add = (Button) findViewById(R.id.btn_crear_com);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Si el nombre esta vacio no se guardara el comentario
                if(txt_nou_nom.getText().toString().isEmpty()){
                    mostrar_toast("El comentario necesita un nombre");

                }// Crear nuevo comentario y guardarlo en la base de datos
                else {
                    // Crear comentario con el texto obtenido de los textview
                    String nombre = String.valueOf(txt_nou_nom.getText());
                    String texto = String.valueOf(txt_nou_txt.getText());
                    Comentarios com = new Comentarios(nombre, texto);

                    //Añadir comentario a la base de datos
                    db.addComentario(com);

                    //Eliminar texto de los textview
                    txt_nou_nom.setText("");
                    txt_nou_txt.setText("");

                    // Añadir al spinner el nuevo comentario
                    actualizarSpinner(db);

                    mostrar_toast("Comentario creado");
                }
            }
        });

        txt_veure_nom = (TextView) findViewById(R.id.txt_veure_nom);
        txt_veure_com = (TextView) findViewById(R.id.txt_veure_com);
        btn_veure = (Button) findViewById(R.id.btn_veure_com);
        btn_veure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Consulta para buscar los comentarios con el nombre seleccionado del spinner
                ArrayList<Comentarios> texto = db.getTextoComentario(spn_com.getSelectedItem().toString());
                if (texto.size()!=0){ // Mostrar comentario si la consulta encontro algun texto
                    txt_veure_nom.setText(spn_com.getSelectedItem().toString());
                    txt_veure_com.setText(texto.get(0).getTexto());
                }else{// No mostrar nada si la consulta no encontro ningun texto
                    txt_veure_nom.setText("");
                    txt_veure_com.setText("");
                }
            }
        });

        btn_delete = (Button) findViewById(R.id.btn_elim_com);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Eliminar el comentario que este seleccionado en el spinner
                db.removeComentario(spn_com.getSelectedItem().toString());

                // Comprobar si se muestra el comentario que se ha borrado y borrarlo si es asi
                if (txt_veure_nom.getText().equals(spn_com.getSelectedItem().toString())){
                    txt_veure_nom.setText("");
                    txt_veure_com.setText("");
                }

                //Actualizar spinner para que no salga el comentario borrado
                actualizarSpinner(db);

                mostrar_toast("Comentario eliminado");
            }
        });

        // Cargar en el spinner los comentarios de la base de datos
        actualizarSpinner(db);

    }

    public void actualizarSpinner(DataBase db){
        ArrayList<Comentarios> coment_list = new ArrayList<Comentarios>();
        ArrayList<String> name_list = new ArrayList<String>();
        coment_list = db.getComentarios();
        name_list.add("");

        for (int i = 0; i < coment_list.size(); i++) {
            Log.d(LOG_TAG, "------Nombre: " + coment_list.get(i).getNombre() + " Comentario: " + coment_list.get(i).getTexto() + "------");
            name_list.add(coment_list.get(i).getNombre());
        }

        // Añadir los nombre de los comentarios al spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, name_list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_com.setAdapter(adapter);
    }

    public void mostrar_toast(String texto) {
        Context context = getApplicationContext();
        CharSequence text = texto;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }


}