package com.example.sala;

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

        ListView listaSalas = (ListView) findViewById(R.id.listaSalas);
        AdaptadorSala adaptadorSala = new AdaptadorSala(this, GestionIncidencias.getArSalas().toArray(new EntSala[0]));

        listaSalas.setAdapter(adaptadorSala);

        //A la ListView de salas le damos un OnItemClickListener para que cuando se pulse sobre una sala nos lleve a la ficha que sea
        listaSalas.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //Creamos el metodo onItemClick, lo sobreescribe el OnItemClickListener de la listView, nos obliga a implementarlo
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Creamos una variable de tipo EntSala para obtener el objeto asociado con e item pulsado
                EntSala salaSeleccionada = (EntSala) adapterView.getItemAtPosition(i);

                //Creamos un intent para poder ir a la ficha de la sala
                Intent intenFichaSala = new Intent((view.getContext()), ficha_sala.class);

                //A ese intent le agregamos los datos usando el metodo putExtra, a este le asignamos un nombre que sera como una ID
                //Y obtenemos el valor que queremos pasar con la variable salaSeleccionada y usando el get correspondiente
                intenFichaSala.putExtra("sala", salaSeleccionada.getCodigoSala());
                intenFichaSala.putExtra("nombre", salaSeleccionada.getNombre());
                intenFichaSala.putExtra("descripcion", salaSeleccionada.getDescripcion());
                //
                //Lanzamos el intent
                startActivity(intenFichaSala);
            }
        });
    }
}