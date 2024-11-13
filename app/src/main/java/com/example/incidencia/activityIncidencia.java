package com.example.incidencia;

import android.annotation.SuppressLint;
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
import com.example.elemento.ficha_elemento;
import com.example.menu3botones;

import gestionincidencias.GestionIncidencias;
import gestionincidencias.entidades.EntElemento;
import gestionincidencias.entidades.EntIncidencia;

public class activityIncidencia extends menu3botones {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_incidencia); //ASEGURARSE DE QUE ESTE EN SU XML
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        ListView listaIncidencias = (ListView) findViewById(R.id.listaIncidencias);
        AdaptadorIncidencia adaptadorIncidencia = new AdaptadorIncidencia(this, GestionIncidencias.getArIncidencias().toArray(new EntIncidencia[0]));

        listaIncidencias.setAdapter(adaptadorIncidencia);


        //A la ListView de salas le damos un OnItemClickListener para que cuando se pulse sobre una sala nos lleve a la ficha que sea
        listaIncidencias.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //Creamos el metodo onItemClick, lo sobreescribe el OnItemClickListener de la listView, nos obliga a implementarlo
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Creamos una variable de tipo EntSala para obtener el objeto asociado con e item pulsado
                EntIncidencia incidenciaSeleccionada = (EntIncidencia) adapterView.getItemAtPosition(i);

                //Creamos un intent para poder ir a la fi   cha de la sala
                Intent intentFichaIncidencia = new Intent((view.getContext()), ficha_incidencia.class);

                //A ese intent le agregamos los datos usando el metodo putExtra, a este le asignamos un nombre que sera como una ID
                //Y obtenemos el valor que queremos pasar con la variable salaSeleccionada y usando el get correspondiente

                intentFichaIncidencia.putExtra("codigoElemento", incidenciaSeleccionada.getCodigoIncidencia());
                intentFichaIncidencia.putExtra("descripcion", incidenciaSeleccionada.getDescripcion());
                intentFichaIncidencia.putExtra("idElemento", incidenciaSeleccionada.getCodigoIncidencia());
                intentFichaIncidencia.putExtra("fechaCreacion", incidenciaSeleccionada.getFechaCreacion());
                intentFichaIncidencia.putExtra("idUsuarioCreacion", String.valueOf(incidenciaSeleccionada.getIdUsuarioCreacion()));
                intentFichaIncidencia.putExtra("elemento", String.valueOf(incidenciaSeleccionada.getElemento()));
                //
                //Lanzamos el intent
                startActivity(intentFichaIncidencia);
            }
        });
    }
}
