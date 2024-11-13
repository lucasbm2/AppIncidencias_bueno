package com.example.usuario;

import static com.example.appincidencias.R.*;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appincidencias.R;
import com.example.menu3botones;
import com.example.ubicacion.ficha_ubicacion;

import gestionincidencias.GestionIncidencias;
import gestionincidencias.entidades.EntUbicacion;
import gestionincidencias.entidades.EntUsuario;

public class activityUsuario extends menu3botones {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(layout.activity_usuario); //ASEGURARSE DE QUE ESTE EN SU XML
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });




        ListView listaUsuarios = (ListView) findViewById(id.listaUsuarios);
        AdaptadorUsuario adaptadorUsuario = new AdaptadorUsuario(this, GestionIncidencias.getArUsuarios().toArray(new EntUsuario[0]));

        listaUsuarios.setAdapter(adaptadorUsuario);


        //A la ListView de salas le damos un OnItemClickListener para que cuando se pulse sobre una sala nos lleve a la ficha que sea
        listaUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //Creamos el metodo onItemClick, lo sobreescribe el OnItemClickListener de la listView, nos obliga a implementarlo
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Creamos una variable de tipo EntSala para obtener el objeto asociado con e item pulsado
                EntUsuario usuarioSeleccionado = (EntUsuario) adapterView.getItemAtPosition(i);

                //Creamos un intent para poder ir a la fi   cha de la sala
                Intent intentFichaUsuario = new Intent((view.getContext()), ficha_usuario.class);

                //A ese intent le agregamos los datos usando el metodo putExtra, a este le asignamos un nombre que sera como una ID
                //Y obtenemos el valor que queremos pasar con la variable salaSeleccionada y usando el get correspondiente

                intentFichaUsuario.putExtra("codigoUsuario", usuarioSeleccionado.getCodigoUsuario());
                intentFichaUsuario.putExtra("nombre", usuarioSeleccionado.getNombre()      );
                intentFichaUsuario.putExtra("correo", usuarioSeleccionado.getCorreo());
                intentFichaUsuario.putExtra("telefono", usuarioSeleccionado.getTelefono());
                intentFichaUsuario.putExtra("password", usuarioSeleccionado.getPassword());
                intentFichaUsuario.putExtra("rol", String.valueOf(usuarioSeleccionado.getRol()));
                //
                //Lanzamos el intent
                startActivity(intentFichaUsuario);
            }
        });

    }
}