    package com.example.tipo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appincidencias.R;

    public class ficha_tipo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha_tipo);

        //Creamos un intent para poder obtener los datos de la otra actividad (activitySalas)
        Intent infoFichaTipo = this.getIntent();

        //Creamos un Bundle para almacenar los datos de la otra actividad (activitySalas)
        Bundle bnd = infoFichaTipo.getExtras();

        //Creamos variables para guardar los datos almacenados en el Bundle, usamos el getString y enre parentesis el nombre
        //que hemos puesto antes en el putExtra, Ejemplo: intenFichaSala.putExtra("sala", salaSeleccionada.getCodigoSala());
        //Esto esta sacado del OnItemClick de la activitySalas. En este caso "sala" seria el nombre
        String codTipo = String.valueOf(bnd.getInt("codigoTipo  "));
        String nombreTipo = bnd.getString("nombreTipo");
        String descripcionTipo = bnd.getString("descripcionTipo");

        //Creamos variables TextView para encontrar el elemento donde queremos mostrar los datos obtenidos.
        //Estos estan en activity_ficha_sala.xml en mi caso.

        TextView txCodigoTipo = findViewById(R.id.codigoTipo);
        txCodigoTipo.setText(String.valueOf(codTipo));
        TextView txNombreTipo = findViewById(R.id.nombreTipo);
        txNombreTipo.setText(String.valueOf(nombreTipo));
        TextView txDescripcionTipo = findViewById(R.id.descripcionTipo);
        txDescripcionTipo.setText(String.valueOf(descripcionTipo));


        Button botonSalir = findViewById(R.id.botonSalir);
        botonSalir.setOnClickListener(v -> finish());
    }
}