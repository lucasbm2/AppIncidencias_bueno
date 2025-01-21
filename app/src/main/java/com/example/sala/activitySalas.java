package com.example.sala;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.example.appincidencias.R;
import com.example.menu3botones;

import java.util.ArrayList;

import gestionincidencias.GestionIncidencias;
import gestionincidencias.entidades.EntSala;

public class activitySalas extends menu3botones {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_salas);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        guardaActividad(getSharedPreferences("datos", MODE_PRIVATE), activitySalas.class.toString());

        //BUSCAMOS LA LISTVIEW DE SALAS
        ListView listaSalas = (ListView) findViewById(R.id.listaSalas);
        //CREAMOS UN ADAPTADOR PARA LA LISTA DE SALAS
        //
        //OBTENEMOS LOS DATOS DE LA BASE DE DATOS
        SalaDatabaseHelper sdh = new SalaDatabaseHelper(this,"BBDDIncidencias", null, 1);
        ArrayList<EntSala> arSalas = sdh.getSalas();
        AdaptadorSala adaptadorSala = new AdaptadorSala(this, arSalas.toArray(new EntSala[0]));
        //
        //FORMA DE OBTENERLO SIN LA BASE DE DATOS, COMO ANTES
//        AdaptadorSala adaptadorSala = new AdaptadorSala(this, GestionIncidencias.getArSalas().toArray(new EntSala[0]));

        //ESTABLECEMOS ADAPTADOR A LA LISTA
        listaSalas.setAdapter(adaptadorSala);

        //OYENTE PARA QUE CUANDO HAGAMOS CLICK SE ABRA LA FICHA SELECCIONADA
        listaSalas.setOnItemClickListener((adapterView, view, position, id) -> {
            EntSala salaSeleccionada = (EntSala) adapterView.getItemAtPosition(position);

            Intent intentFichaSala = new Intent(view.getContext(), ficha_sala.class);

            intentFichaSala.putExtra("codigoSala", salaSeleccionada.getCodigoSala());
            intentFichaSala.putExtra("nombreSala", salaSeleccionada.getNombre());
            intentFichaSala.putExtra("descripcionSala", salaSeleccionada.getDescripcion());

            startActivity(intentFichaSala);
        });

        //BOTONM Y FUNCION PARA AÑADIR UNA NUEVA SALA
        Button añadirSala = findViewById(R.id.añadirSala);
        añadirSala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAnadirSala = new Intent(view.getContext(), ficha_sala.class);

                intentAnadirSala.putExtra("codigoSala", 0);
                intentAnadirSala.putExtra("nombreSala", "");
                intentAnadirSala.putExtra("descripcionSala", "");

                startActivity(intentAnadirSala);
            }
        });
    }
}
