package com.example.elemento;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.style.AlignmentSpan;
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
import com.example.sala.ficha_sala;

import gestionincidencias.GestionIncidencias;
import gestionincidencias.entidades.EntElemento;
import gestionincidencias.entidades.EntSala;

public class activityElemento extends menu3botones {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_elemento); //ASEGURARSE DE QUE ESTE EN SU XML
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        ListView listaElementos = (ListView) findViewById(R.id.listaElementos);
        AdaptadorElemento adaptadorElemento = new AdaptadorElemento(this, GestionIncidencias.getArElementos().toArray(new EntElemento[0]));

        listaElementos.setAdapter(adaptadorElemento);

        //A la ListView de salas le damos un OnItemClickListener para que cuando se pulse sobre una sala nos lleve a la ficha que sea
        listaElementos.setOnItemClickListener((adapterView, view, position, id) -> {
            EntElemento elementoSeleccionado = (EntElemento) listaElementos.getItemAtPosition(position);

            Intent intentFichaElemento = new Intent(view.getContext(), ficha_elemento.class);

            intentFichaElemento.putExtra("codigoElemento", elementoSeleccionado.getCodigoElemento());
            intentFichaElemento.putExtra("nombreElemento", elementoSeleccionado.getNombre());
            intentFichaElemento.putExtra("descripcionElemento", elementoSeleccionado.getDescripcion());
            intentFichaElemento.putExtra("idTipoElemento", elementoSeleccionado.getIdTipo());
            intentFichaElemento.putExtra("tipoElemento", elementoSeleccionado.getTipoElemento().getNombre());

            //Lanzamos el intent
            startActivity(intentFichaElemento);
        });
//        Button añadirElemento = findViewById(R.id.añadirElemento);
//        añadirElemento.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            Intent intentAnadirElemento = new Intent(view.getContext(), ficha_elemento.class);
//
//            intentAnadirElemento.putExtra("codigoElemento", 0);
//            intentAnadirElemento.putExtra("nombreElemento", "");
//            intentAnadirElemento.putExtra("descripcionElemento", "");
//            intentAnadirElemento.putExtra("idTipoElemento", 0);
//            intentAnadirElemento.putExtra("tipoElemento", "Seleccione un tipo de elemento");
//            startActivity(intentAnadirElemento);
//        }
//    });
}
}