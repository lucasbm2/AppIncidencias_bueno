    package com.example.ubicacion;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appincidencias.R;

    public class ficha_ubicacion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha_ubicacion);

        //Creamos un intent para poder obtener los datos de la otra actividad (activitySalas)
        Intent infoFichaUbicacion = this.getIntent();

        //Creamos un Bundle para almacenar los datos de la otra actividad (activitySalas)
        Bundle bnd = infoFichaUbicacion.getExtras();

        //Creamos variables para guardar los datos almacenados en el Bundle, usamos el getString y enre parentesis el nombre
        //que hemos puesto antes en el putExtra, Ejemplo: intenFichaSala.putExtra("sala", salaSeleccionada.getCodigoSala());
        //Esto esta sacado del OnItemClick de la activitySalas. En este caso "sala" seria el nombre
        String codUbicacion = String.valueOf(bnd.getInt("codigoUbicacion"));
        String idSala = String.valueOf(bnd.getInt("idSala "));
        String idElemento = String.valueOf(bnd.getInt("idElemento"));
        String descripcionUbicacion = bnd.getString("descripcion");
        String fechaInicio = bnd.getString("fechaInicio");
        String fechaFin = String.valueOf(bnd.getInt("fechaFin"));
        String sala = bnd.getString("sala");
        String elemento = bnd.getString("elemento");

        //Creamos variables TextView para encontrar el elemento donde queremos mostrar los datos obtenidos.
        //Estos estan en activity_ficha_sala.xml en mi caso.
        TextView txCodUbicacion = findViewById(R.id.codigoUbicacion);
        txCodUbicacion.setText(String.valueOf(codUbicacion));
        TextView txIdSala = findViewById(R.id.idSala);
        txIdSala.setText(String.valueOf(idSala));
        TextView txIdElemento = findViewById(R.id.idElemento);
        txIdElemento.setText(String.valueOf(idElemento));
        TextView txDescripcion = findViewById(R.id.descripcion);
        txDescripcion.setText(String.valueOf(descripcionUbicacion));
        TextView txFechaInicio = findViewById(R.id.fechaInicio);
        txFechaInicio.setText(String.valueOf(fechaInicio));
        TextView txFechaFin = findViewById(R.id.fechaFin);
        txFechaFin.setText(String.valueOf(fechaFin));
        TextView txSala = findViewById(R.id.sala);
        txSala.setText(String.valueOf(sala));
        TextView txElemento = findViewById(R.id.elemento);
        txElemento.setText(String.valueOf(elemento));


        Button botonSalir = findViewById(R.id.botonSalir);
        botonSalir.setOnClickListener(v -> finish());
    }
}