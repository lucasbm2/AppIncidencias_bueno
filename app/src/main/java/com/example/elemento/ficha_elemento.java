package com.example.elemento;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appincidencias.R;

public class ficha_elemento extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha_elemento);

        //Creamos un intent para poder obtener los datos de la otra actividad (activitySalas)
        Intent infoFichaElemento = this.getIntent();

        //Creamos un Bundle para almacenar los datos de la otra actividad (activitySalas)
        Bundle bnd = infoFichaElemento.getExtras();

        //Creamos variables para guardar los datos almacenados en el Bundle, usamos el getString y enre parentesis el nombre
        //que hemos puesto antes en el putExtra, Ejemplo: intenFichaSala.putExtra("sala", salaSeleccionada.getCodigoSala());
        //Esto esta sacado del OnItemClick de la activitySalas. En este caso "sala" seria el nombre
        String codElemento = String.valueOf(bnd.getInt("codigoElemento"));
        String nombreElemento = bnd.getString("nombre");
        String descripcionElemento = bnd.getString("descripcion");
        //IMPORTANTE COMPROBAR QUE SEA DEL TIPO CORRECTO,
        //AQUI LO TENIA COMO GETSTRING, PERO ERA UN INT, ENTONCES NO MOSTRABA
        String idTipo = String.valueOf(bnd.getInt("idTipo"));
        String tipoElemento = bnd.getString("tipoElemento");

        //Creamos variables TextView para encontrar el elemento donde queremos mostrar los datos obtenidos.
        //Estos estan en activity_ficha_sala.xml en mi caso.
        TextView txCodElemento = findViewById(R.id.codigoElemento);
        txCodElemento.setText(String.valueOf(codElemento));
        TextView txNombreElemento = findViewById(R.id.nombreElemento);
        txNombreElemento.setText(String.valueOf(nombreElemento));
        TextView txDescripElem = findViewById(R.id.descripcionElemento);
        txDescripElem.setText(String.valueOf(descripcionElemento));
        TextView txIdTipo = findViewById(R.id.idTipo);
        txIdTipo.setText(String.valueOf(idTipo));
        TextView txTipoElemento = findViewById(R.id.tipoElemento);
        txTipoElemento.setText(String.valueOf(tipoElemento));

    }
}