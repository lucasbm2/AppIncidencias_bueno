package com.example.ubicacion;

import static com.example.appincidencias.R.id;
import static com.example.appincidencias.R.layout;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.menu3botones;

import gestionincidencias.GestionIncidencias;
import gestionincidencias.entidades.EntUbicacion;

public class activityUbicacion extends menu3botones {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(layout.activity_ubicacion); // Asegúrate de que esté en su XML
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ListView listaUbicaciones = (ListView) findViewById(id.listaUbicaciones); // Cambia a listaUbicaciones
        AdaptadorUbicacion AdaptadorUbicacion = new AdaptadorUbicacion(this, GestionIncidencias.getArUbicaciones().toArray(new EntUbicacion[0])); // Usa ubicaciones como argumento

        listaUbicaciones.setAdapter(AdaptadorUbicacion); // Asigna el adaptador a la lista
    }
}
