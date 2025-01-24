package com.example.sala;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appincidencias.R;

import gestionincidencias.GestionIncidencias;
import gestionincidencias.entidades.EntSala;

public class ficha_sala extends AppCompatActivity {

    private EntSala sala;
    //DECLARO LA SALADATABASEHELPER
    private SalaDatabaseHelper salaHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha_sala);
        //INICIALIZO MI SALADATABASEHELPER
        salaHelper = new SalaDatabaseHelper(this, "BBDDIncidencias", null, 1);

        //EXTRAEMOS LOS DATOS DE LA LISTA
        int codSala = getIntent().getExtras().getInt("codigoSala");

        //PARA MODIFICAR
        if (codSala > 0) {
            sala = salaHelper.getSala(codSala);
            //PARA AÑADIR UNA NUEVA SALA
        } else if (codSala == 0) {
            sala = new EntSala(0, "", "");
        }

        //RECOJO DATOS DEL XML
        if (sala != null) {
            TextView txCodigoSala = findViewById(R.id.codigoSala);
            txCodigoSala.setText(String.valueOf(sala.getCodigoSala()));

            EditText txNombre = findViewById(R.id.nombreSala);
            txNombre.setText(String.valueOf(sala.getNombre()));

            EditText txDescripcion = findViewById(R.id.descripcionSala);
            txDescripcion.setText(String.valueOf(sala.getDescripcion()));
        }

        Button botonVolver = findViewById(R.id.botonSalir);
        botonVolver.setOnClickListener(view -> {
            Intent intent = new Intent(ficha_sala.this, activitySalas.class);
            startActivity(intent);
        });

        //BOTON PARA GUARDAR
        Button botonGuardar = findViewById(R.id.botonGuardar);
        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sala != null) {

                    TextView txCodigoSala = findViewById(R.id.codigoSala);
                    EditText txNombre = findViewById(R.id.nombreSala);
                    EditText txDescripcion = findViewById(R.id.descripcionSala);

                    sala.setCodigoSala(Integer.parseInt(txCodigoSala.getText().toString()));
                    sala.setNombre(txNombre.getText().toString());
                    sala.setDescripcion(txDescripcion.getText().toString());

                    //PARA MODIFICAR LOS DATOS DE UNA SALA YA CREADA
                    if (sala.getCodigoSala() > 0) {
                        salaHelper.actualizarSala(sala);

                        //PARA AÑADIR NUEVO CODIGO
                    } else if (sala.getCodigoSala() == 0) {
                        salaHelper.crearSala(sala);
                    }

                    Toast toast = Toast.makeText(getApplicationContext(), "Sala guardada correctamente", Toast.LENGTH_SHORT);
                    toast.show();

                    Intent intent = new Intent(ficha_sala.this, activitySalas.class);

                    startActivity(intent);
                }
            }
        });


        Button botonEliminar = findViewById(R.id.botonEliminar);
        botonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!sala.getNombre().isEmpty()) {

                    Intent eliminar = new Intent(view.getContext(), activitySalas.class);
                    Toast.makeText(view.getContext(), "Sala eliminada correctamente", Toast.LENGTH_SHORT).show();
                    salaHelper.borrarSala(sala.getCodigoSala());
                    startActivity(eliminar);
                } else {
                    Toast.makeText(view.getContext(), "No se puede eliminar la sala", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
