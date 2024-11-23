package com.example.usuario;

import static com.example.appincidencias.R.*;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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
        listaUsuarios.setOnItemClickListener((adapterView, view, position, id) -> {
            EntUsuario usuarioSeleccionado = (EntUsuario) adapterView.getItemAtPosition(position);


            //Creamos un intent para poder ir a la fi   cha de la sala
            Intent intentFichaUsuario = new Intent((view.getContext()), ficha_usuario.class);

            //A ese intent le agregamos los datos usando el metodo putExtra, a este le asignamos un nombre que sera como una ID
            //Y obtenemos el valor que queremos pasar con la variable salaSeleccionada y usando el get correspondiente

            intentFichaUsuario.putExtra("codigoUsuario", usuarioSeleccionado.getCodigoUsuario());
            intentFichaUsuario.putExtra("nombreUsuario", usuarioSeleccionado.getNombre());
            intentFichaUsuario.putExtra("rolUsuario", usuarioSeleccionado.getRol());
            //
            //Lanzamos el inten t
            startActivity(intentFichaUsuario);

        });

        //BOTON Y FUNCION PARA AÑADIR UNA NUEVO USUARIO
        Button añadirUsuario = findViewById(R.id.añadirUsuario);
        añadirUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAñadirUsuario = new Intent(view.getContext(), ficha_usuario.class);

                intentAñadirUsuario.putExtra("codigoUsuario", 0);
                intentAñadirUsuario.putExtra("nombreUsuario", "");
                intentAñadirUsuario.putExtra("rolUsuario", "");
                //
                startActivity(intentAñadirUsuario);
            }
        });
    }
}