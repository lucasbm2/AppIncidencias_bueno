package com.example.ubicacion;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appincidencias.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import gestionincidencias.GestionIncidencias;
import gestionincidencias.entidades.EntUbicacion;

public class ficha_ubicacion extends AppCompatActivity {

    private EntUbicacion ubicacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha_ubicacion);

        // Extraer los datos del Bundle
        int codUbicacion = getIntent().getExtras().getInt("codigoUbicacion");
        String descripcionUbicacion = getIntent().getExtras().getString("descripcion");

        // Buscar la ubicación si el código es mayor que 0
        if (codUbicacion > 0) {
            for (EntUbicacion u : GestionIncidencias.getArUbicaciones()) {
                if (u.getCodigoUbicacion() == codUbicacion) {
                    ubicacion = u;
                }
            }

        } else if (codUbicacion == 0 && descripcionUbicacion.isEmpty()) {
            // Crear una nueva ubicación vacía si el código es 0 y la descripción está vacía
            ubicacion = new EntUbicacion(0, 0, 0, "", null, null);
            //POR SI EL ELEMENTO TIENE CODIGO 0
        } else if (codUbicacion == 0) {
            for (EntUbicacion u : GestionIncidencias.getArUbicaciones()) {
                if (u.getCodigoUbicacion() == codUbicacion) {
                    ubicacion = u;
                }
            }
        }
        if (ubicacion != null) {
            TextView txcodUbicacion = findViewById(R.id.codigoUbicacionFicha);
            txcodUbicacion.setText(String.valueOf(ubicacion.getCodigoUbicacion()));

            TextView txDescripcion = findViewById(R.id.descripcionUbicacion);
            txDescripcion.setText(ubicacion.getDescripcion());

            TextView txIdSala = findViewById(R.id.idSalaUbicacion);
            txIdSala.setText(String.valueOf(ubicacion.getIdSala()));

            TextView txIdElemento = findViewById(R.id.idElementoFichaUbicacion);
            txIdElemento.setText(String.valueOf(ubicacion.getIdElemento()));

            TextView txFechaInicio = findViewById(R.id.fechaInicioUbicacion);
            Date fechaInicio = ubicacion.getFechaInicio();
            if (fechaInicio != null) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                txFechaInicio.setText(formatter.format(fechaInicio));
            } else {
                txFechaInicio.setText("Fecha no disponible");
            }

            TextView txFechaFin = findViewById(R.id.fechaFinUbicacion);
            Date fechaFin = ubicacion.getFechaFin();
            if (fechaFin != null) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                txFechaFin.setText(formatter.format(fechaFin));
            } else {
                txFechaFin.setText("Fecha no disponible");
            }
        }

        // Botón para volver a la lista de ubicaciones
        Button botonVolver = findViewById(R.id.botonSalir);
        botonVolver.setOnClickListener(view -> {
            Intent intent = new Intent(ficha_ubicacion.this, activityUbicacion.class);
            startActivity(intent);
        });


        Button botonGuardar = findViewById(R.id.botonGuardar);
        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ubicacion != null) {
                    EditText txtCodigo = findViewById(R.id.codigoUbicacionFicha);
                    EditText txtDescripcion = findViewById(R.id.descripcionUbicacion);
                    EditText txtIdSala = findViewById(R.id.idSalaUbicacion);
                    EditText txtIdElemento = findViewById(R.id.idElementoFichaUbicacion);
                    TextView txtFechaInicio = findViewById(R.id.fechaInicioUbicacion);
                    String fechaIni = txtFechaInicio.getText().toString();
                    SimpleDateFormat formatterIni = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                    TextView txtFechaFin = findViewById(R.id.fechaFinUbicacion);
                    String fechaFin = txtFechaFin.getText().toString();
                    SimpleDateFormat formatterFin = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

                    if (ubicacion.getCodigoUbicacion() != 0 ) {

                        ubicacion.setCodigoUbicacion(Integer.parseInt(txtCodigo.getText().toString()));
                        ubicacion.setDescripcion(txtDescripcion.getText().toString());
                        ubicacion.setIdSala(Integer.parseInt(txtIdSala.getText().toString()));
                        ubicacion.setIdElemento(Integer.parseInt(txtIdElemento.getText().toString()));

                        try {
                            Date fechaInicio = formatterIni.parse(fechaIni);
                            ubicacion.setFechaInicio(fechaInicio);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        try {
                            Date fechaFinal = formatterFin.parse(fechaFin);
                            ubicacion.setFechaFin(fechaFinal);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    } else if (ubicacion.getCodigoUbicacion() == 0 && ubicacion.getDescripcion().isEmpty()) {

                        ubicacion.setCodigoUbicacion(Integer.parseInt(txtCodigo.getText().toString()));
                        ubicacion.setDescripcion(txtDescripcion.getText().toString());
                        ubicacion.setIdSala(Integer.parseInt(txtIdSala.getText().toString()));
                        ubicacion.setIdElemento(Integer.parseInt(txtIdElemento.getText().toString()));

                        try {
                            Date fechaInicio = formatterIni.parse(fechaIni);
                            ubicacion.setFechaInicio(fechaInicio);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        try {
                            Date fechaFinal = formatterFin.parse(fechaFin);
                            ubicacion.setFechaFin(fechaFinal);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        GestionIncidencias.getArUbicaciones().add(GestionIncidencias.getArUbicaciones().size(), ubicacion);
                    } else if (ubicacion.getCodigoUbicacion() == 0) {

                        ubicacion.setCodigoUbicacion(Integer.parseInt(txtCodigo.getText().toString()));
                        ubicacion.setDescripcion(txtDescripcion.getText().toString());
                        ubicacion.setIdSala(Integer.parseInt(txtIdSala.getText().toString()));
                        ubicacion.setIdElemento(Integer.parseInt(txtIdElemento.getText().toString()));

                        try {
                            Date fechaInicio = formatterIni.parse(fechaIni);
                            ubicacion.setFechaInicio(fechaInicio);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        try {
                            Date fechaFinal = formatterFin.parse(fechaFin);
                            ubicacion.setFechaFin(fechaFinal);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                }
                Intent intent = new Intent(ficha_ubicacion.this, activityUbicacion.class);
                startActivity(intent);
            }

        });

    }
}
