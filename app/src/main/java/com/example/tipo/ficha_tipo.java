package com.example.tipo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appincidencias.R;
import com.example.elemento.activityElemento;
import com.example.ubicacion.activityUbicacion;
import com.example.ubicacion.ficha_ubicacion;

import java.util.ArrayList;

import gestionincidencias.GestionIncidencias;
import gestionincidencias.entidades.EntTipo;
import gestionincidencias.entidades.EntUbicacion;

public class ficha_tipo extends AppCompatActivity {

    private EntTipo tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha_tipo);

        //Creamos un intent para poder obtener los datos de la otra actividad (activitySalas)
        int codigoTipo = getIntent().getIntExtra("codigoTipo", 0);
        String nombreTipo = getIntent().getStringExtra("nombreTipo");
        String descripcionTipo = getIntent().getStringExtra("descripcionTipo");

        if (codigoTipo > 0) {
            for (EntTipo t : GestionIncidencias.getArTipos()) {
                if (t.getCodigoTipo() == codigoTipo) {
                    tipo = t;
                }
            }
        } else if (codigoTipo == 0 && descripcionTipo.isEmpty()) {
            tipo = new EntTipo(0, "", "");
        } else if (codigoTipo == 0) {
            for (EntTipo t : GestionIncidencias.getArTipos()) {
                if (t.getDescripcion().equals(descripcionTipo)) {
                    tipo = t;
                }
            }
        }

        if (tipo != null) {
            TextView txCodigo = findViewById(R.id.codigoTipo);
            txCodigo.setText(String.valueOf(tipo.getCodigoTipo()));

            TextView txNombre = findViewById(R.id.nombreTipo);
            txNombre.setText(String.valueOf(tipo.getNombre()));

            TextView txDescripcion = findViewById(R.id.descripcionTipo);
            txDescripcion.setText(String.valueOf(tipo.getDescripcion()));

        }
        // Botón para volver a la lista de ubicaciones
        Button botonVolver = findViewById(R.id.botonSalir);
        botonVolver.setOnClickListener(view -> {
            Intent intent = new Intent(ficha_tipo.this, activityTipo.class);
            startActivity(intent);
        });
        //Aqui creo el boton para guardar y su funcionamiento
        Button botonGuardar = findViewById(R.id.botonGuardar);
        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tipo != null) {
                    TextView txCodigo = findViewById(R.id.codigoTipo);
                    TextView txNombre = findViewById(R.id.nombreTipo);
                    TextView txDescripcion = findViewById(R.id.descripcionTipo);

                    //MODIFICO TIPO
                    if (tipo.getCodigoTipo() != 0) {
                        tipo.setCodigoTipo(Integer.parseInt(txCodigo.getText().toString()));
                        tipo.setNombre(txNombre.getText().toString());
                        tipo.setDescripcion(txDescripcion.getText().toString());
                    }
                    //AÑADO NUEVO TIPO
                    else if (tipo.getCodigoTipo() == 0 && tipo.getDescripcion().isEmpty()) {
                        boolean encontrado = false;

                        for (EntTipo t : GestionIncidencias.getArTipos()) {
                            if (t.getNombre().equals(txNombre.getText().toString())) {
                                encontrado = true;
                                break;
                            }
                        }

                        if (!encontrado) {
                            int siguienteCodigo = 1;
                            for (EntTipo t : GestionIncidencias.getArTipos()) {
                                if (t.getCodigoTipo() >= siguienteCodigo) {
                                    siguienteCodigo = t.getCodigoTipo() + 1;
                                }
                            }

                            tipo.setCodigoTipo(siguienteCodigo);
                            tipo.setNombre(txNombre.getText().toString());
                            tipo.setDescripcion(txDescripcion.getText().toString());
                        }
                        GestionIncidencias.getArTipos().add(GestionIncidencias.getArTipos().size(), tipo);
                    }

                    Toast toast = Toast.makeText(getApplicationContext(), "Tipo guardado correctamente", Toast.LENGTH_SHORT);
                    toast.show();

                }
                Intent intent = new Intent(ficha_tipo.this, activityTipo.class);
                startActivity(intent);
            }
        });

        Button botonEliminar = findViewById(R.id.botonEliminar);
        botonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tipo.getNombre().isEmpty()) {

                    Intent eliminar = new Intent(view.getContext(), activityTipo.class);
                    Toast.makeText(view.getContext(), "Tipo eliminado correctamente", Toast.LENGTH_SHORT).show();
                    GestionIncidencias.getArTipos().remove(tipo);
                    startActivity(eliminar);
                } else {
                    Toast.makeText(view.getContext(), "No se puede eliminar un tipo sin nombre", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
