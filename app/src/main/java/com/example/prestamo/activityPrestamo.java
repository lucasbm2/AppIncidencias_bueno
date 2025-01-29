package com.example.prestamo;

import android.annotation.SuppressLint;
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
import com.example.incidencia.ficha_incidencia;
import com.example.menu3botones;
import com.example.ubicacion.ficha_ubicacion;

import gestionincidencias.GestionIncidencias;
import gestionincidencias.entidades.EntIncidencia;
import gestionincidencias.entidades.EntPrestamo;

public class activityPrestamo extends menu3botones {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_prestamo); //ASEGURARSE DE QUE ESTE EN SU XML
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });




        ListView listaPrestamos = (ListView) findViewById(R.id.listaPrestamos);
        AdaptadorPrestamo adaptadorPrestamo = new AdaptadorPrestamo(this, GestionIncidencias.getArPrestamos().toArray(new EntPrestamo[0]));

        listaPrestamos.setAdapter(adaptadorPrestamo);


        //A la ListView de salas le damos un OnItemClickListener para que cuando se pulse sobre una sala nos lleve a la ficha que sea
        listaPrestamos.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //Creamos el metodo onItemClick, lo sobreescribe el OnItemClickListener de la listView, nos obliga a implementarlo
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Creamos una variable de tipo EntSala para obtener el objeto asociado con e item pulsado
                EntPrestamo prestamoSeleccionado = (EntPrestamo) adapterView.getItemAtPosition(i);

                //Creamos un intent para poder ir a la fi   cha de la sala
                Intent intentFichaPrestamo = new Intent((view.getContext()), ficha_prestamo.class);

                //A ese intent le agregamos los datos usando el metodo putExtra, a este le asignamos un nombre que sera como una ID
                //Y obtenemos el valor que queremos pasar con la variable salaSeleccionada y usando el get correspondiente

                intentFichaPrestamo.putExtra("codigoPrestamo", prestamoSeleccionado.getCodigoPrestamo());
                intentFichaPrestamo.putExtra("nombreUsuarioPrestamo ", prestamoSeleccionado.getIdUsuario());
                intentFichaPrestamo.putExtra("nombreElementoPrestamo", prestamoSeleccionado.getIdElemento());
                //
                //Lanzamos el intent
                startActivity(intentFichaPrestamo);
            }
        });

        //BOTON Y FUNCION PARA AÑADIR UNA NUEVA UBICACION
        Button añadirPrestamo = findViewById(R.id.añadirPrestamo);
        añadirPrestamo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAñadirPrestamo = new Intent(view.getContext(), ficha_prestamo.class);

                intentAñadirPrestamo.putExtra("codigoUbicacion", 0);
                intentAñadirPrestamo.putExtra("nombreUsuarioPrestamo", "");
                intentAñadirPrestamo.putExtra("nombreElementoPrestamo", "");

                startActivity(intentAñadirPrestamo);
            }
        });
    }
}
