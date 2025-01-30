package com.example.incidencia;

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
import com.example.usuario.UsuarioDatabaseHelper;

import java.util.ArrayList;

import gestionincidencias.entidades.EntElemento;
import gestionincidencias.entidades.EntIncidencia;
import gestionincidencias.entidades.EntUbicacion;
import gestionincidencias.entidades.EntUsuario;

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
        ListView listaIncidencias = (ListView) findViewById(R.id.listaIncidencias);
        //Adaptador para la lista
        IncidenciaDatabaseHelper incidenciaHelper = new IncidenciaDatabaseHelper(this, "BBDDIncidencias", null, 1);
        ArrayList<EntIncidencia> arIncidencias = incidenciaHelper.getIncidencias();

        AdaptadorIncidencia adaptadorIncidencia = new AdaptadorIncidencia(this, arIncidencias.toArray(new EntIncidencia[0]));

        UsuarioDatabaseHelper udh = new UsuarioDatabaseHelper(this, "BBDDIncidencias", null, 1);
        ArrayList<EntUsuario> arUsuarios = udh.getUsuarios();
        for (EntIncidencia incidencia : arIncidencias) {
            for (EntUsuario usuario : arUsuarios) {
                if (incidencia.getIdUsuarioCreacion() == usuario.getCodigoUsuario()) {
                    incidencia.setUsuarioCreacion(usuario);
                    break;
                }
            }
        }

        ElementoDBHelper edh = new ElementoDBHelper(this, "BBDDIncidencias", null, 1);
        ArrayList<EntElemento> arElementos = edh.getElementos();
        for (EntIncidencia inc : arIncidencias) {
            for (EntElemento elem : arElementos) {
                if (inc.getIdElemento() == elem.getCodigoElemento()) {
                    inc.setElemento(elem);
                    break;
                }
            }
        }


        //Asignamos el adaptador a la lista
        listaIncidencias.setAdapter(adaptadorIncidencia);

        //Al hacer click en una incidencia
        listaIncidencias.setOnItemClickListener((adapterView, view, position, id) -> {

            EntIncidencia incidenciaSeleccionada = (EntIncidencia) adapterView.getItemAtPosition(position);
            Intent intentFichaIncidencia = new Intent(view.getContext(), ficha_incidencia.class);
            intentFichaIncidencia.putExtra("codigoIncidencia", incidenciaSeleccionada.getCodigoIncidencia());
            intentFichaIncidencia.putExtra("descripcion", incidenciaSeleccionada.getDescripcion());

            startActivity(intentFichaIncidencia);
        });

        Button añadirIncidencia = findViewById(R.id.añadirIncidencia);
        añadirIncidencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAñadirIncidencia = new Intent(view.getContext(), ficha_incidencia.class);

                intentAñadirIncidencia.putExtra("codigoIncidencia", 0);
                intentAñadirIncidencia.putExtra("descripcionIncidencia", "");

                startActivity(intentAñadirIncidencia);
            }
        });
    }
}
