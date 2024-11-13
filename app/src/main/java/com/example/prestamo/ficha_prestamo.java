    package com.example.prestamo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appincidencias.R;

    public class        ficha_prestamo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha_prestamo);

        //Creamos un intent para poder obtener los datos de la otra actividad (activitySalas)
        Intent infoFichaPrestamo = this.getIntent();

        //Creamos un Bundle para almacenar los datos de la otra actividad (activitySalas)
        Bundle bnd = infoFichaPrestamo.getExtras();

        //Creamos variables para guardar los datos almacenados en el Bundle, usamos el getString y enre parentesis el nombre
        //que hemos puesto antes en el putExtra, Ejemplo: intenFichaSala.putExtra("sala", salaSeleccionada.getCodigoSala());
        //Esto esta sacado del OnItemClick de la activitySalas. En este caso "sala" seria el nombre
        String codPrestamo = String.valueOf(bnd.getInt("codigoPrestamo"));
        String idUsuario = String.valueOf(bnd.getInt("idUsuario "));
        String idElemento = String.valueOf(bnd.getInt("idElemento"));
        String fechaInicio = bnd.getString("fechaInicio");
        String fechaFin = String.valueOf(bnd.getInt("fechaFin"));
        String usuario = bnd.getString("usuario");
        String elemento = bnd.getString("elemento");

        //Creamos variables TextView para encontrar el elemento donde queremos mostrar los datos obtenidos.
        //Estos estan en activity_ficha_sala.xml en mi caso.
        TextView txcodPrestamo = findViewById(R.id.codigoPrestamo);
        txcodPrestamo.setText(String.valueOf(codPrestamo));
        TextView txidUsuario = findViewById(R.id.idUsuario);
        txidUsuario.setText(String.valueOf(idUsuario));
        TextView txidElemento = findViewById(R.id.idElemento);
        txidElemento.setText(String.valueOf(idElemento));
        TextView txfechaInicio = findViewById(R.id.fechaInicio);
        txfechaInicio.setText(String.valueOf(fechaInicio));
        TextView txfechaFin = findViewById(R.id.fechaFin);
        txfechaFin.setText(String.valueOf(fechaFin));
        TextView txUsuario = findViewById(R.id.usuario);
        txUsuario.setText(String.valueOf(usuario));
        TextView txElemento = findViewById(R.id.elemento);
        txElemento.setText(String.valueOf(elemento));


        Button botonSalir = findViewById(R.id.botonSalir);
        botonSalir.setOnClickListener(v -> finish());
    }
}