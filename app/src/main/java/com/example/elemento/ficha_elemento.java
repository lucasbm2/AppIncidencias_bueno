package com.example.elemento;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appincidencias.R;
import com.example.ubicacion.activityUbicacion;
import com.example.ubicacion.ficha_ubicacion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import gestionincidencias.GestionIncidencias;
import gestionincidencias.entidades.EntElemento;
import gestionincidencias.entidades.EntTipo;
import gestionincidencias.entidades.EntUbicacion;

public class ficha_elemento extends AppCompatActivity {

    private EntElemento elemento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha_elemento);

        //Extraer los datos del bundle
        int codElemento = getIntent().getExtras().getInt("codigoElemento");
        String nombreElemento = getIntent().getExtras().getString("nombreElemento");
        String descripcionElemento = getIntent().getExtras().getString("descripcionElemento");

        //PARA MODIFICAR UN ELEMENTO
        if (codElemento > 0) {
            for (EntElemento ele : GestionIncidencias.getArElementos()) {
                //Recorro EntUbicacion y si coincide el codigo lo asigno a ubicacion
                if (ele.getCodigoElemento() == codElemento) {
                    elemento = ele;
                }
            }

            //PARA AÑADIR UN NUEVO ELEMENTO
        } else if (codElemento == 0 && descripcionElemento.isEmpty()) {
            // CREAR UNA NUEVA UBICACIÓN VACÍA SI EL CÓDIGO ES 0 Y LA DESCRIPCIÓN ESTÁ VACÍA
            elemento = new EntElemento(0, "", "", 0);

            //POR SI EL ELEMENTO TIENE CODIGO 0 Y LA DESCRIPCION NO ESTA VACIA
        } else if (codElemento == 0) {
            for (EntElemento ele : GestionIncidencias.getArElementos()) {
                if (ele.getCodigoElemento() == codElemento) {
                    elemento = ele;
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

            //PARA MOSTRAR SPINNER DE NOMBRES DE TIPO
            //LISTA CODIGOS DE TIPO
            ArrayList<String> tipos = new ArrayList<>();
            for (EntTipo tipo : GestionIncidencias.getArTipos()) {
                tipos.add(tipo.getNombre());
            }

            Collections.sort(tipos);

            Spinner spinnerTipo = findViewById(R.id.spinnerNombreTipoElementoFichaElemento);

            ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, tipos);
            spinnerTipo.setAdapter(adapterSpinner);
            if (elemento != null && elemento.getTipoElemento() != null && elemento.getTipoElemento().getNombre() != null) {
                spinnerTipo.setSelection(tipos.indexOf(elemento.getTipoElemento().getNombre()));
            }
            //FIN SPINNER

        }
        // Botón para volver a la lista de ELEMENTOS
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
                    EditText txCodElemento = findViewById(R.id.codigoElemento);
                    EditText txNombreElemento = findViewById(R.id.nombreElemento);
                    EditText txDescripcionElemento = findViewById(R.id.descripcionElemento);

                    Spinner spinnerTipo = findViewById(R.id.spinnerNombreTipoElementoFichaElemento);
                    Object tipoSeleccionado = spinnerTipo.getSelectedItem().toString();


                    //MODIFICO ELEMENTO
                    if (elemento.getCodigoElemento() != 0) {
                        elemento.setCodigoElemento(Integer.parseInt(txCodElemento.getText().toString()));
                        elemento.setNombre(txNombreElemento.getText().toString());
                        elemento.setDescripcion(txDescripcionElemento.getText().toString());

                        for (EntTipo tipo : GestionIncidencias.getArTipos()) {
                            if (tipo.getNombre().equals(tipoSeleccionado)) {
                                elemento.setIdTipo(tipo.getCodigoTipo());
                                elemento.setTipoElemento(tipo);
                            }
                        }
                    } else if (elemento.getCodigoElemento() == 0 && elemento.getDescripcion().isEmpty()) {
                        boolean encontrado = false;

                        for (EntTipo tipo : GestionIncidencias.getArTipos()) {
                            if (tipo.getCodigoTipo() == elemento.getCodigoElemento()) {
                                encontrado = true;
                                break;
                            }
                        }

                        if (!encontrado) {
                            int siguienteCodigo = 1;
                            for (EntTipo tipo : GestionIncidencias.getArTipos()) {
                                if (tipo.getCodigoTipo() >= siguienteCodigo) {
                                    siguienteCodigo = tipo.getCodigoTipo() + 1;
                                }
                            }

                            elemento.setCodigoElemento(siguienteCodigo);
                            elemento.setNombre(txNombreElemento.getText().toString());
                            elemento.setDescripcion(txDescripcionElemento.getText().toString());

                            for (EntTipo tipo : GestionIncidencias.getArTipos()) {
                                if (tipo.getNombre().equals(tipoSeleccionado)) {
                                    elemento.setCodigoElemento(tipo.getCodigoTipo());
                                    elemento.setTipoElemento(tipo);
                                }
                            }
                        }
                        GestionIncidencias.getArElementos().add(GestionIncidencias.getArElementos().size(), elemento);
                    } else if (elemento.getCodigoElemento() == 0) {
                        elemento.setCodigoElemento(Integer.parseInt(txCodElemento.getText().toString()));
                        elemento.setNombre(txNombreElemento.getText().toString());
                        elemento.setDescripcion(txDescripcionElemento.getText().toString());

                        for (EntTipo tipo : GestionIncidencias.getArTipos()) {
                            if (tipo.getNombre().equals(tipoSeleccionado)) {
                                elemento.setIdTipo(tipo.getCodigoTipo());
                                elemento.setTipoElemento(tipo);
                            }
                        }
                    }
                }
                Intent intent = new Intent(ficha_elemento.this, activityElemento.class);
                startActivity(intent);
            }
        });
    }
}