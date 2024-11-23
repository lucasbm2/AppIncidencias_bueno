package com.example.sala;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appincidencias.R;

import gestionincidencias.GestionIncidencias;
import gestionincidencias.entidades.EntSala;

public class ficha_sala extends AppCompatActivity {

    private EntSala sala;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha_sala);

        //EXTRAEMOS LOS DATOS DE LA LISTA
        int codSala = getIntent().getExtras().getInt("codigoSala");
        String nombreSala = getIntent().getExtras().getString("nombreSala");
        String descripcionSala = getIntent().getExtras().getString("descripcionSala");

        //PARA MODIFICAR
        if (codSala > 0) {
            for (EntSala s : GestionIncidencias.getArSalas()) {
                if (s.getCodigoSala() == codSala) {
                    sala = s;
                }
            }
            //NUEVA SALA
        } else if (codSala == 0 && nombreSala.isEmpty()) {
            sala = new EntSala(0, "", "");

            //POR SI EL CODIGO ES 0
        } else if (codSala == 0) {
            for (EntSala s : GestionIncidencias.getArSalas()) {
                if (s.getCodigoSala() == codSala) {
                    sala = s;
                }
            }
        }

        //RECOJO DATOS DEL XML
        if (sala != null) {
            EditText txCodigoSala = findViewById(R.id.codigoSala);
            txCodigoSala.setText(String.valueOf(sala.getCodigoSala()));

            EditText txNombre = findViewById(R.id.nombreSala);
            txNombre.setText(String.valueOf(sala.getNombre()));

            EditText txDescripcion = findViewById(R.id.descripcionSala);
            txDescripcion.setText(String.valueOf(sala.getDescripcion()));

        }

        Button botonVolver = findViewById(R.id.botonSalir);
        botonVolver.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), activitySalas.class);
            startActivity(intent);
        });

        //BOTON PARA GUARDAR
        Button botonGuardar = findViewById(R.id.botonGuardar);
        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sala != null) {

                   EditText txCodigoSala = findViewById(R.id.codigoSala);
                   sala.setCodigoSala(Integer.parseInt(txCodigoSala.getText().toString()));

                   EditText txNombre = findViewById(R.id.nombreSala);
                   sala.setNombre(txNombre.getText().toString());

                   EditText txDescripcion = findViewById(R.id.descripcionSala);
                   sala.setDescripcion(txDescripcion.getText().toString());

                   //PARA MODIFICAR LOS DATOS DE UNA SALA YA CREADA
                    if (sala.getCodigoSala() != 0) {
                        sala.setCodigoSala(Integer.parseInt(txCodigoSala.getText().toString()));
                        sala.setNombre(txNombre.getText().toString());
                        sala.setDescripcion(txDescripcion.getText().toString());

                        //PARA AÑADIR NUEVO CODIGO
                    } else if (sala.getCodigoSala() == 0 && sala.getNombre().isEmpty()) {
                        boolean encontrado = false;

                        for (EntSala s : GestionIncidencias.getArSalas()) {
                            if (s.getCodigoSala() == sala.getCodigoSala()) {
                                encontrado = true;
                                break;
                            }
                        }

                        if (!encontrado) {
                            int siguienteCodigo = 1;

                            for (EntSala s : GestionIncidencias.getArSalas()) {
                                if (s.getCodigoSala() >= siguienteCodigo) {
                                    siguienteCodigo = s.getCodigoSala() +1;
                                }
                            }
                            sala.setCodigoSala(siguienteCodigo);
                            sala.setNombre(txNombre.getText().toString());
                            sala.setDescripcion(txDescripcion.getText().toString());

                        }
                        GestionIncidencias.getArSalas().add(GestionIncidencias.getArSalas().size(), sala);
                        //SI CODIGO ES 0
                    } else if (sala.getCodigoSala() == 0) {
                        sala.setCodigoSala(GestionIncidencias.getArSalas().size() + 1);
                        sala.setNombre(txNombre.getText().toString());
                        sala.setDescripcion(txDescripcion.getText().toString());
                    }
                }
                Intent intent = new Intent(ficha_sala.this, activitySalas.class);
                startActivity(intent);
            }
        });
    }
}
