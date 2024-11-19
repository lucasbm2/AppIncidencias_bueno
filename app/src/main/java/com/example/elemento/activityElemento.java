package com.example.elemento;

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
                listaElementos.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    //Creamos el metodo onItemClick, lo sobreescribe el OnItemClickListener de la listView, nos obliga a implementarlo
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        //Creamos una variable de tipo EntSala para obtener el objeto asociado con e item pulsado
                        EntElemento elementoSeleccionado = (EntElemento) adapterView.getItemAtPosition(i);

                        //Creamos un intent para poder ir a la fi   cha de la sala
                        Intent intentFichaElemento = new Intent((view.getContext()), ficha_elemento.class);

                        //A ese intent le agregamos los datos usando el metodo putExtra, a este le asignamos un nombre que sera como una ID
                        //Y obtenemos el valor que queremos pasar con la variable salaSeleccionada y usando el get correspondiente

                        intentFichaElemento.putExtra("codigoElemento", elementoSeleccionado.getCodigoElemento());
                        intentFichaElemento.putExtra("nombre", elementoSeleccionado.getNombre());
                        intentFichaElemento.putExtra("descripcion", elementoSeleccionado.getDescripcion());
                        intentFichaElemento.putExtra("idTipo", elementoSeleccionado.getIdTipo());
                        intentFichaElemento.putExtra("tipoElemento", String.valueOf(elementoSeleccionado.getTipoElemento().getNombre()));
                        //
                        //Lanzamos el intent
                        startActivity(intentFichaElemento);
                    }
                });
    }
}