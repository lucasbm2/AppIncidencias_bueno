package com.example.tipo;

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
import com.example.prestamo.ficha_prestamo;
import com.example.ubicacion.ficha_ubicacion;

import java.util.ArrayList;

import gestionincidencias.GestionIncidencias;
import gestionincidencias.entidades.EntPrestamo;
import gestionincidencias.entidades.EntTipo;

public class activityTipo extends menu3botones {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tipo);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        guardaActividad(getSharedPreferences("datos", MODE_PRIVATE), activityTipo.class.toString());


        ListView listaTipos = (ListView) findViewById(R.id.listaTipos);
        TipoDatabaseHelper tdh = new TipoDatabaseHelper(this, "BBDDIncidencias", null, 1);
        ArrayList<EntTipo> arTipos = tdh.getTipos();
        AdaptadorTipo adaptadorTipo = new AdaptadorTipo(this, tdh.getTipos().toArray(new EntTipo[0]));

        listaTipos.setAdapter(adaptadorTipo);


        //A la ListView de salas le damos un OnItemClickListener para que cuando se pulse sobre una sala nos lleve a la ficha que sea
        listaTipos.setOnItemClickListener((adapterView, view, position, l) -> {
            EntTipo tipoSeleccionado = (EntTipo) adapterView.getItemAtPosition(position);

            Intent intentFichaTipo = new Intent((view.getContext()), ficha_tipo.class);
            intentFichaTipo.putExtra("codigoTipo", tipoSeleccionado.getCodigoTipo());
            intentFichaTipo.putExtra("nombreTipo", tipoSeleccionado.getNombre());
            intentFichaTipo.putExtra("descripcionTipo", tipoSeleccionado.getDescripcion());

            startActivity(intentFichaTipo);
        });

        //BOTON Y FUNCION PARA AÑADIR UN NUEVO TIPO
        Button añadirTipo = findViewById(R.id.añadirTipo);
        añadirTipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAñadirTipo = new Intent(view.getContext(), ficha_tipo.class);

                intentAñadirTipo.putExtra("codigoTipo", 0);
                intentAñadirTipo.putExtra("nombreTipo", "");
                intentAñadirTipo.putExtra("descripcionTipo", "");

                startActivity(intentAñadirTipo);
            }
        });
    }
}