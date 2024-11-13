    package com.example.incidencia;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appincidencias.R;

import java.util.Date;

    public class ficha_incidencia extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha_incidencia);

        //Creamos un intent para poder obtener los datos de la otra actividad (activitySalas)
        Intent infoFichaIncidencia = this.getIntent();

        //Creamos un Bundle para almacenar los datos de la otra actividad (activitySalas)
        Bundle bnd = infoFichaIncidencia.getExtras();

        //Creamos variables para guardar los datos almacenados en el Bundle, usamos el getString y enre parentesis el nombre
        //que hemos puesto antes en el putExtra, Ejemplo: intenFichaSala.putExtra("sala", salaSeleccionada.getCodigoSala());
        //Esto esta sacado del OnItemClick de la activitySalas. En este caso "sala" seria el nombre
        String codIncidencia = String.valueOf(bnd.getInt("codigoIncidencia"));
        String descripcionIncidencia = bnd.getString("descripcion");
        String idElemento = String.valueOf(bnd.getInt("idElemento"));
        String fechaCreacion = bnd.getString("fechaCreacion");
        String idUsuarioCreacion = String.valueOf(bnd.getInt("idUsuarioCreacion"));
        String usuarioCreacion = bnd.getString("usuarioCreacion");
        String elemento = bnd.getString("elemento");

        //Creamos variables TextView para encontrar el elemento donde queremos mostrar los datos obtenidos.
        //Estos estan en activity_ficha_sala.xml en mi caso.
        TextView txCodIncidencia = findViewById(R.id.codigoIncidencia);
        txCodIncidencia.setText(String.valueOf(codIncidencia));
        TextView txDescripIncidencia = findViewById(R.id.descripcionIncidencia);
        txDescripIncidencia.setText(String.valueOf(descripcionIncidencia));
        TextView txIdElemento = findViewById(R.id.idElemento);
        txIdElemento.setText(String.valueOf(idElemento));
        TextView txFechaCreacion = findViewById(R.id.fechaCreacion);
        txFechaCreacion.setText(String.valueOf(fechaCreacion));
        TextView txIdUsuarioCreacion = findViewById(R.id.idUsuarioCreacion);
        txIdUsuarioCreacion.setText(String.valueOf(idUsuarioCreacion));
            TextView txElemento = findViewById(R.id.elemento);
        txElemento.setText(String.valueOf(elemento));


        Button botonSalir = findViewById(R.id.botonSalir);
        botonSalir.setOnClickListener(v -> finish());
    }
}