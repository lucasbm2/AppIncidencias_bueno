package com.example.ubicacion;

import static com.example.appincidencias.R.id;
import static com.example.appincidencias.R.layout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.menu3botones;
import com.example.prestamo.ficha_prestamo;

import gestionincidencias.GestionIncidencias;
import gestionincidencias.entidades.EntPrestamo;
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


        //A la ListView de salas le damos un OnItemClickListener para que cuando se pulse sobre una sala nos lleve a la ficha que sea
        listaUbicaciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //Creamos el metodo onItemClick, lo sobreescribe el OnItemClickListener de la listView, nos obliga a implementarlo
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Creamos una variable de tipo EntSala para obtener el objeto asociado con e item pulsado
                EntUbicacion ubicacionSeleccionada = (EntUbicacion) adapterView.getItemAtPosition(i);

                //Creamos un intent para poder ir a la fi   cha de la sala
                Intent intentFichaUbicacion = new Intent((view.getContext()), ficha_ubicacion.class);

                //A ese intent le agregamos los datos usando el metodo putExtra, a este le asignamos un nombre que sera como una ID
                //Y obtenemos el valor que queremos pasar con la variable salaSeleccionada y usando el get correspondiente

                intentFichaUbicacion.putExtra("codigoUbicacion", ubicacionSeleccionada.getCodigoUbicacion());
                intentFichaUbicacion.putExtra("idSala", ubicacionSeleccionada.getIdSala()   );
                intentFichaUbicacion.putExtra("idElemento", ubicacionSeleccionada.getIdElemento());
                intentFichaUbicacion.putExtra("descripcion", ubicacionSeleccionada.getDescripcion());
                intentFichaUbicacion.putExtra("fechaInicio", ubicacionSeleccionada.getFechaInicio());
                intentFichaUbicacion.putExtra("fechaFin", String.valueOf(ubicacionSeleccionada.getFechaFin()));
                intentFichaUbicacion.putExtra("sala", String.valueOf(ubicacionSeleccionada.getSala()));
                intentFichaUbicacion.putExtra("elemento", String.valueOf(ubicacionSeleccionada.getElemento()));
                //
                //Lanzamos el intent
                startActivity(intentFichaUbicacion);
            }
        });
    }
}
