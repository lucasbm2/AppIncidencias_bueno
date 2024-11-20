package com.example.ubicacion;

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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.List;

import gestionincidencias.GestionIncidencias;
import gestionincidencias.entidades.EntUbicacion;

public class activityUbicacion extends menu3botones {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ubicacion);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ListView listaUbicaciones = (ListView) findViewById(R.id.listaUbicaciones);
        AdaptadorUbicacion adaptadorUbicacion = new AdaptadorUbicacion(this,
                    GestionIncidencias.getArUbicaciones().toArray(new EntUbicacion[0]));

        listaUbicaciones.setAdapter(adaptadorUbicacion);

        listaUbicaciones.setOnItemClickListener((adapterView, view, position, id) -> {
            EntUbicacion ubicacionSeleccionada = (EntUbicacion) adapterView.getItemAtPosition(position);

            Intent intentFichaUbicacion = new Intent(view.getContext(), ficha_ubicacion.class);
            intentFichaUbicacion.putExtra("codigoUbicacion", ubicacionSeleccionada.getCodigoUbicacion());
            intentFichaUbicacion.putExtra("descripcion", ubicacionSeleccionada.getDescripcion());

            startActivity(intentFichaUbicacion);
        });
        Button añadirUbicacion = findViewById(R.id.añadirUbicacion);
        añadirUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAñadirUbicacion = new Intent(view.getContext(), ficha_ubicacion.class);

                intentAñadirUbicacion.putExtra("codigoUbicacion", 0);
                intentAñadirUbicacion.putExtra("descripcion", "");

                startActivity(intentAñadirUbicacion);
            }
        });
    }
}
