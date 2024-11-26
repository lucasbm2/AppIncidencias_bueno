package com.example.incidencia;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appincidencias.R;

public class ficha_incidencia extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha_incidencia);

        //Obtenemos los datos de la actividad anterior
        Intent infoFichaIncidencia = this.getIntent();
        Bundle bnd = infoFichaIncidencia.getExtras();

        //Guardamos los datos en variables
        int codIncidencia = bnd.getInt("codigoIncidencia");
        String descripcionIncidencia = bnd.getString("descripcion");
        int idElemento = bnd.getInt("idElemento");
        String fechaCreacion = bnd.getString("fechaCreacion");
        String idUsuarioCreacion = bnd.getString("idUsuarioCreacion");
        String usuarioCreacion = bnd.getString("usuarioCreacion");
        String elemento = bnd.getString("elemento");

//        //Asociamos las variables con los TextViews correspondientes
//        TextView txCodIncidencia = findViewById(R.id.codigoIncidencia);
//        txCodIncidencia.setText(String.valueOf(codIncidencia));
//        TextView txDescripIncidencia = findViewById(R.id.descripcionIncidencia);
//        txDescripIncidencia.setText(descripcionIncidencia);
//        TextView txIdElemento = findViewById(R.id.idElemento);
//        txIdElemento.setText(String.valueOf(idElemento));
//        TextView txFechaCreacion = findViewById(R.id.fechaCreacion);
//        txFechaCreacion.setText(fechaCreacion);
//        TextView txIdUsuarioCreacion = findViewById(R.id.idUsuarioCreacion);
//        txIdUsuarioCreacion.setText(idUsuarioCreacion);
//        TextView txUsuarioCreacion = findViewById(R.id.usuarioCreacion);
//        txUsuarioCreacion.setText(usuarioCreacion);
//        TextView txElemento = findViewById(R.id.elemento);
//        txElemento.setText(elemento);

        //Botón para salir de la ficha
        Button botonSalir = findViewById(R.id.botonSalir);
        botonSalir.setOnClickListener(v -> finish());
    }
}
