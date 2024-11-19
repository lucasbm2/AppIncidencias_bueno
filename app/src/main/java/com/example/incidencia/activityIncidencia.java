package com.example.incidencia;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appincidencias.R;
import com.example.menu3botones;

import java.text.SimpleDateFormat;

import gestionincidencias.GestionIncidencias;
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

        //Lista de incidencias
        ListView listaIncidencias = findViewById(R.id.listaIncidencias);
        AdaptadorIncidencia adaptadorIncidencia = new AdaptadorIncidencia(this, GestionIncidencias.getArIncidencias().toArray(new EntIncidencia[0]));
        listaIncidencias.setAdapter(adaptadorIncidencia);

        //Al hacer click en una incidencia
        listaIncidencias.setOnItemClickListener((adapterView, view, i, l) -> {
            //Obtenemos la incidencia seleccionada
            EntIncidencia incidenciaSeleccionada = (EntIncidencia) adapterView.getItemAtPosition(i);

            //Formateamos la fecha
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String fechaFormateada = sdf.format(incidenciaSeleccionada.getFechaCreacion());

            //Creamos un Intent para abrir la ficha de la incidencia
            Intent intentFichaIncidencia = new Intent(view.getContext(), ficha_incidencia.class);

            //Pasamos los datos al Intent
            intentFichaIncidencia.putExtra("codigoIncidencia", incidenciaSeleccionada.getCodigoIncidencia());
            intentFichaIncidencia.putExtra("descripcion", incidenciaSeleccionada.getDescripcion());
            intentFichaIncidencia.putExtra("idElemento", incidenciaSeleccionada.getIdElemento());
            intentFichaIncidencia.putExtra("fechaCreacion", fechaFormateada);
            intentFichaIncidencia.putExtra("idUsuarioCreacion", String.valueOf(incidenciaSeleccionada.getIdUsuarioCreacion()));

            intentFichaIncidencia.putExtra("usuarioCreacion",String.valueOf(incidenciaSeleccionada.getUsuarioCreacion().getNombre()));
            intentFichaIncidencia.putExtra("elemento", incidenciaSeleccionada.getElemento().getNombre());

            //Lanzamos la actividad
            startActivity(intentFichaIncidencia);
        });
    }
}
