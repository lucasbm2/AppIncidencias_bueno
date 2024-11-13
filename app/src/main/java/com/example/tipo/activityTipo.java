package com.example.tipo;

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
import com.example.prestamo.ficha_prestamo;

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
            //
        });



        ListView listaTipos = (ListView) findViewById(R.id.listaTipos);
        AdaptadorTipo adaptadorTipo = new AdaptadorTipo(this, GestionIncidencias.getArTipos().toArray(new EntTipo[0]));

        listaTipos.setAdapter(adaptadorTipo);


        //A la ListView de salas le damos un OnItemClickListener para que cuando se pulse sobre una sala nos lleve a la ficha que sea
        listaTipos.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //Creamos el metodo onItemClick, lo sobreescribe el OnItemClickListener de la listView, nos obliga a implementarlo
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Creamos una variable de tipo EntSala para obtener el objeto asociado con e item pulsado
                EntTipo tipoSeleccionado = (EntTipo) adapterView.getItemAtPosition(i);

                //Creamos un intent para poder ir a la fi   cha de la sala
                Intent intentFichaTipo = new Intent((view.getContext()), ficha_tipo.class);

                //A ese intent le agregamos los datos usando el metodo putExtra, a este le asignamos un nombre que sera como una ID
                //Y obtenemos el valor que queremos pasar con la variable salaSeleccionada y usando el get correspondiente

                intentFichaTipo.putExtra("codigoTipo", tipoSeleccionado.getCodigoTipo());
                intentFichaTipo.putExtra("nombreTipo", tipoSeleccionado.getNombre());
                intentFichaTipo.putExtra("descripcionTipo", tipoSeleccionado.getDescripcion());
                //
                //Lanzamos el intent
                startActivity(intentFichaTipo);
            }
        });
    }
}