package com.example.elemento;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appincidencias.R;
import com.example.tipo.TipoDatabaseHelper;
import com.example.ubicacion.activityUbicacion;
import com.example.ubicacion.ficha_ubicacion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import gestionincidencias.entidades.EntElemento;
import gestionincidencias.entidades.EntTipo;


public class ficha_elemento extends AppCompatActivity {

    private EntElemento elemento;
    private ElementoDBHelper elementoHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha_elemento);

        ElementoDBHelper edh = new ElementoDBHelper(this, "BBDDIncidencias", null, 1);
        ArrayList<EntElemento> arElementos = edh.getElementos();
        AdaptadorElemento adaptadorElemento = new AdaptadorElemento(this, arElementos.toArray(new EntElemento[0]));

        TipoDatabaseHelper tdh = new TipoDatabaseHelper(this, "BBDDIncidencias", null, 1);
        ArrayList<EntTipo> arTipos = tdh.getTipos();

        int codElemento = getIntent().getExtras().getInt("codigoElemento");

        elementoHelper = new ElementoDBHelper(this, "BBDDIncidencias", null, 1);

        if (codElemento > 0) {
            elemento = elementoHelper.getElemento(codElemento);
        } else if (codElemento == 0) {
            elemento = new EntElemento(0, "", "", 0);
        }

        for (EntTipo tipo : arTipos) {
            if (tipo != null && elemento != null) {
            if (tipo.getCodigoTipo() == elemento.getIdTipo()) {
                elemento.setTipoElemento(tipo);
                break;
            }
            }
        }

        if (elemento != null) {
            TextView txCodElemento = findViewById(R.id.codigoElemento);
            txCodElemento.setText(String.valueOf(elemento.getCodigoElemento()));
            TextView txNombreElemento = findViewById(R.id.nombreElemento);
            txNombreElemento.setText(elemento.getNombre());
            TextView txDescripcionElemento = findViewById(R.id.descripcionElemento);
            txDescripcionElemento.setText(elemento.getDescripcion());

            ArrayList<String> tipos = new ArrayList<>();
            for (EntTipo tipo : arTipos) {
                tipos.add(tipo.getNombre());
            }

            Collections.sort(tipos);

            Spinner spinnerTipo = findViewById(R.id.spinnerNombreTipoElementoFichaElemento);

            ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, tipos);
            spinnerTipo.setAdapter(adapterSpinner);
            if (elemento != null && elemento.getTipoElemento() != null && elemento.getTipoElemento().getNombre() != null) {
                spinnerTipo.setSelection(tipos.indexOf(elemento.getTipoElemento().getNombre()));
            }

        }

        Button botonVolver = findViewById(R.id.botonSalir);
        botonVolver.setOnClickListener(view -> {
            Intent intent = new Intent(ficha_elemento.this, activityElemento.class);
            startActivity(intent);
        });

        Button botonGuardar = findViewById(R.id.botonGuardar);
        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (elemento != null) {
                    TextView txCodElemento = findViewById(R.id.codigoElemento);
                    EditText txNombreElemento = findViewById(R.id.nombreElemento);
                    EditText txDescripcionElemento = findViewById(R.id.descripcionElemento);

                    Spinner spinnerTipo = findViewById(R.id.spinnerNombreTipoElementoFichaElemento);
                    Object tipoSeleccionado = spinnerTipo.getSelectedItem().toString();

                    // Actualizar los valores en el objeto elemento
                    elemento.setCodigoElemento(Integer.parseInt(txCodElemento.getText().toString()));
                    elemento.setNombre(txNombreElemento.getText().toString());
                    elemento.setDescripcion(txDescripcionElemento.getText().toString());

                    for (EntTipo tipo : arTipos) {
                        if (tipo.getNombre().equals(tipoSeleccionado)) {
                            elemento.setIdTipo(tipo.getCodigoTipo());
                            elemento.setTipoElemento(tipo);
                            break;
                        }
                    }

                    // Guardar o actualizar el elemento en la base de datos
                    if (elemento.getCodigoElemento() > 0) {
                        elementoHelper.actualizarElemento(elemento);
                    } else if (elemento.getCodigoElemento() == 0) {
                        long id = elementoHelper.crearElemento(elemento);
                        elemento.setCodigoElemento((int) id); // Actualiza el ID del elemento con el generado
                    }

                    Toast.makeText(getApplicationContext(), "Elemento guardado correctamente", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ficha_elemento.this, activityElemento.class);
                    startActivity(intent);
                }
            }
        });


        Button botonEliminar = findViewById(R.id.botonEliminar);
        botonEliminar.

                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!elemento.getNombre().isEmpty()) {

                            Intent eliminar = new Intent(view.getContext(), activityElemento.class);
                            Toast.makeText(view.getContext(), "Elemento eliminado correctamente", Toast.LENGTH_SHORT).show();
                            elementoHelper.borrarElemento(elemento.getCodigoElemento());
                            startActivity(eliminar);
                        } else {
                            Toast.makeText(view.getContext(), "No se puede eliminar un elemento sin nombre", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
