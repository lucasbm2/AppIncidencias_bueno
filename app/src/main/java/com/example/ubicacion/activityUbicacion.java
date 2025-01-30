package com.example.ubicacion;

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
import com.example.elemento.ElementoDBHelper;
import com.example.menu3botones;
import com.example.sala.SalaDatabaseHelper;
import java.util.ArrayList;
import gestionincidencias.entidades.EntElemento;
import gestionincidencias.entidades.EntSala;
import gestionincidencias.entidades.EntUbicacion;

public class activityUbicacion extends menu3botones {

    @SuppressLint("MissingInflatedId")
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

        //BUSCAMOS LA LISTVIEW DE UBICACIONES
        ListView listaUbicaciones = (ListView) findViewById(R.id.listaUbicaciones);
        //CREAMOS UN ADAPTADOR PARA LA LISTA
        UbicacionDatabaseHelper ubicacionHelper = new UbicacionDatabaseHelper(this, "BBDDIncidencias", null, 1);
        ArrayList<EntUbicacion> arUbicaciones = ubicacionHelper.getUbicaciones();

        AdaptadorUbicacion adaptadorUbicacion = new AdaptadorUbicacion(this, arUbicaciones.toArray(new EntUbicacion[0]));

        SalaDatabaseHelper sdh = new SalaDatabaseHelper(this, "BBDDIncidencias", null, 1);
        ArrayList<EntSala> arSalas = sdh.getSalas();
        for (EntUbicacion ub : arUbicaciones) {
            for (EntSala s : arSalas) {
                if (ub.getIdSala() == s.getCodigoSala()) {
                    ub.setSala(s);
                    break;
                }
            }
        }

        ElementoDBHelper edh = new ElementoDBHelper(this, "BBDDIncidencias", null, 1);
        ArrayList<EntElemento> arElementos = edh.getElementos();
        for (EntUbicacion ub : arUbicaciones) {
            for (EntElemento e : arElementos) {
                if (ub.getIdElemento() == e.getCodigoElemento()) {
                    ub.setElemento(e);
                    break;
                }
            }
        }

        //ESTABLECEMOS ADAPTADOR A LA LISTA
        listaUbicaciones.setAdapter(adaptadorUbicacion);

        //OYENTE PARA QUE CUANDO HAGAMOS CLICK SE ABRA LA FICHA SELECCIONADA
        listaUbicaciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                EntUbicacion ubicacionSeleccionada = (EntUbicacion) adapterView.getItemAtPosition(i);

                Intent intentFichaUbicacion = new Intent(view.getContext(), ficha_ubicacion.class);
                intentFichaUbicacion.putExtra("codigoUbicacion", ubicacionSeleccionada.getCodigoUbicacion());
                intentFichaUbicacion.putExtra("descripcionUbicacion", ubicacionSeleccionada.getDescripcion());

                startActivity(intentFichaUbicacion);
            }
        });

//        BOTON Y FUNCION PARA AÑADIR UNA NUEVA UBICACION
        Button añadirUbicacion = findViewById(R.id.añadirUbicacion);
        añadirUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAñadirUbicacion = new Intent(view.getContext(), ficha_ubicacion.class);

                intentAñadirUbicacion.putExtra("codigoUbicacion", 0);
                intentAñadirUbicacion.putExtra("descripcionUbicacion", "");

                startActivity(intentAñadirUbicacion);
            }
        });
    }
}
