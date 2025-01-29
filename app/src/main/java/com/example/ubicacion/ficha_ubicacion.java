package com.example.ubicacion;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appincidencias.R;
import com.example.elemento.ElementoDBHelper;
import com.example.elemento.activityElemento;
import com.example.sala.SalaDatabaseHelper;
import com.example.tipo.activityTipo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

import gestionincidencias.entidades.EntElemento;
import gestionincidencias.entidades.EntSala;
import gestionincidencias.entidades.EntUbicacion;

public class ficha_ubicacion extends AppCompatActivity {

    //Creo una ubicacion
    private EntUbicacion ubicacion;
    private UbicacionDatabaseHelper ubicacionHelper;
    //Este es el formato a aplicar a las fechas despues
    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha_ubicacion);

        ubicacionHelper = new UbicacionDatabaseHelper(this, "BBDDIncidencias", null, 1);

        ///////
        ArrayList<EntSala> arSalas = new ArrayList<>();
        SalaDatabaseHelper sdh = new SalaDatabaseHelper(this, "BBDDIncidencias", null, 1);
        arSalas = sdh.getSalas();
        //////
        ArrayList<EntElemento> arElementos = new ArrayList<>();
        ElementoDBHelper edh = new ElementoDBHelper(this, "BBDDIncidencias", null, 1);
        arElementos = edh.getElementos();

        // Extraer los datos del Bundle de listas
        int codUbicacion = getIntent().getExtras().getInt("codigoUbicacion");
        String descripcionUbicacion = getIntent().getExtras().getString("descripcionUbicacion");

        //PARA MODIFICAR LA UBICACION
        // Buscar la ubicación si el código es mayor que 0 para saber que ya existe (AL ASIGNAR LO ASIGNO AL SIGUIENTE NUM DISPONIBLE)
        if (codUbicacion > 0) {
            try {
                ubicacion = ubicacionHelper.getUbicacion(codUbicacion);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            //PARA CUANDO AÑADO UNA NUEVA UBICACIÓN
        } else if (codUbicacion == 0) {
            ubicacion = new EntUbicacion(0, 0, 0, "", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));


            //RECOJO LOS DATOS DEL XML Y LOS ASIGNO
            if (ubicacion != null) {
                TextView txcodUbicacion = findViewById(R.id.codigoUbicacionFicha);
                txcodUbicacion.setText(String.valueOf(ubicacion.getCodigoUbicacion()));

                TextView txDescripcion = findViewById(R.id.descripcionUbicacion);
                txDescripcion.setText(ubicacion.getDescripcion());

                if (ubicacion.getIdSala() > 0) {
                    for (EntSala sala : arSalas) {
                        if (sala.getCodigoSala() == ubicacion.getIdSala()) {
                            ubicacion.setSala(sala);
                            break;
                        }
                    }
                }

                if (ubicacion.getIdElemento() > 0) {
                    for (EntElemento ele : arElementos) {
                        if (ele.getCodigoElemento() == ubicacion.getIdElemento()) {
                            ubicacion.setElemento(ele);
                            break;
                        }
                    }
                }

                //PARA MOSTRAR EL SPINNER DE NOMBRES DE ELEMENTO
                //LISTA CODIGOS DE ELEMENTO

                //PARA MOSTRAR EL SPINNER DE NOMBRES DE SALA
                //LISTA CODIGOS DE SALA
                ArrayList<String> salas = new ArrayList<>();
                for (EntSala sala : arSalas) {
                    salas.add(sala.getNombre());
                }

                Collections.sort(salas);

                //Encuentro el spinner y lo asigno a una variable spinnerSala
                Spinner spinnerSala = findViewById(R.id.spinnerIdSalaFichaUbicacion);
                //Creo un adaptador pa  ra el spinner, con el contexto, tipo de spinner y la lista que hemos creado antes
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, salas);
                //Asigno el adaptador al spinner
                spinnerSala.setAdapter(spinnerArrayAdapter);
                //Compruebo que la ubicacion no sea null y que tenga sala y entonces asigno el elemento seleccionado al spinner
                if (ubicacion != null) {
                    spinnerSala.setSelection(salas.indexOf(ubicacion.getSala().getNombre()));
                }

                //AQUI ASIGNO LOS VALORES QUE SE VAN A MOSTRAR EN EL SPINNER
                ArrayList<String> elementos = new ArrayList<>();
                for (EntElemento ele : arElementos) {
                    //Para controlar que no repita varias veces las variables en el spinner
                    if (!elementos.contains(ele.getTipoElemento().getNombre())) {
                        elementos.add(String.valueOf(ele.getTipoElemento().getNombre()));
                    }
                }

                Collections.sort(elementos);

                //Encuentro el spinner y lo asigno a una variable spinnerSala
                Spinner spinnerElemento = findViewById(R.id.spinnerElementoFichaUbicacion);
                //Creo un adaptador para el spinner, con el contexto, tipo de spinner y la lista que hemos creado antes
                ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, elementos);
                //Asigno el adaptador al spinner
                spinnerElemento.setAdapter(spinnerArrayAdapter2);
                //Compruebo que la ubicacion no sea null y que tenga sala y entonces asigno el elemento seleccionado al spinner
                if (ubicacion != null) {
                    spinnerElemento.setSelection(elementos.indexOf(ubicacion.getElemento().getDescripcion()));
                }


                //CONTROLO QUE LAS FECHAS NO SEAN NULAS
                TextView txFechaInicio = findViewById(R.id.editarFechaInicioUbicacion);
                if (ubicacion.getFechaInicio() != null) {
                    txFechaInicio.setText(df.format(ubicacion.getFechaInicio()));
                } else {
                    txFechaInicio.setText("Fecha no disponible");
                }

                TextView txFechaFin = findViewById(R.id.editarFechaFinUbicacion);
                if (ubicacion.getFechaFin() != null) {
                    txFechaFin.setText(df.format(ubicacion.getFechaFin()));
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


            //Aqui creo el boton para guardar y su funcionamiento
            Button botonGuardar = findViewById(R.id.botonGuardar);
            ArrayList<EntSala> finalArSalas = arSalas;
            ArrayList<EntElemento> finalArElementos = arElementos;
            botonGuardar.setOnClickListener(new View.OnClickListener() {
                @Override
                //Metodo para guardar la ubicacion con los datos introducidos
                public void onClick(View view) {
                    if (ubicacion != null) {

                        TextView txtCodigo = findViewById(R.id.codigoUbicacionFicha);
                        EditText txtDescripcion = findViewById(R.id.descripcionUbicacion);

                        Spinner spinnerSala = findViewById(R.id.spinnerIdSalaFichaUbicacion);
                        Object salaSeleccionada = spinnerSala.getSelectedItem().toString();

                        Spinner spinnerElemento = findViewById(R.id.spinnerElementoFichaUbicacion);
                        Object elementoSeleccionado = spinnerElemento.getSelectedItem().toString();

                        TextView txtFechaInicio = findViewById(R.id.editarFechaInicioUbicacion);
                        String fechaIni = txtFechaInicio.getText().toString();
                        SimpleDateFormat formatterIni = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

                        TextView txtFechaFin = findViewById(R.id.editarFechaFinUbicacion);
                        String fechaFin = txtFechaFin.getText().toString();
                        SimpleDateFormat formatterFin = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

                        ubicacion.setCodigoUbicacion(Integer.parseInt(txtCodigo.getText().toString()));
                        ubicacion.setDescripcion(txtDescripcion.getText().toString());

                        for (EntSala sala : finalArSalas) {
                            if (sala.getNombre().equals(salaSeleccionada)) {
                                ubicacion.setIdSala(sala.getCodigoSala());
                                ubicacion.setSala(sala);
                                break;
                            }
                        }

                        for (EntElemento elem : finalArElementos) {
                            if (elem.getNombre().equals(elementoSeleccionado)) {
                                ubicacion.setIdElemento(elem.getCodigoElemento());
                                ubicacion.setElemento(elem);
                                break;
                            }
                        }

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

                        if (ubicacion.getCodigoUbicacion() > 0) {
                            ubicacionHelper.actualizarUbicacion(ubicacion);
                        } else if (ubicacion.getCodigoUbicacion() == 0) {
                            long id = ubicacionHelper.crearUbicacion(ubicacion);
                            ubicacion.setCodigoUbicacion((int) id);
                        }

                        Toast toast = Toast.makeText(ficha_ubicacion.this, "Ubicacion Guardada", Toast.LENGTH_SHORT);
                        toast.show();

                    }
                    Intent intent = new Intent(ficha_ubicacion.this, activityUbicacion.class);
                    startActivity(intent);
                }
            });

            TextView txtFechaInicio = findViewById(R.id.editarFechaInicioUbicacion);
            txtFechaInicio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar calen = Calendar.getInstance();
                    calen.setTime(ubicacion.getFechaInicio());
                    DatePickerDialog dialogo = new DatePickerDialog(ficha_ubicacion.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int y, int m, int d) {
                            TextView txtFechaInicio = findViewById(R.id.editarFechaInicioUbicacion);
                            Date fechaInicio = ubicacion.getFechaInicio();
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                            String[] fecha = formatter.format(fechaInicio).split(" ");
                            txtFechaInicio.setText(y + "-" + (m + 1) + "-" + d + " " + fecha[1]);
                        }
                    }, calen.get(Calendar.YEAR), calen.get(Calendar.MONTH), calen.get(Calendar.DAY_OF_MONTH));
                    dialogo.show();
                }
            });

            TextView txtFechaFin = findViewById(R.id.editarFechaFinUbicacion);
            if (ubicacion.getFechaFin() != null) {
                txtFechaFin.setText(df.format(ubicacion.getFechaFin()));
            } else {
                ubicacion.setFechaFin(new Date());
                txtFechaFin.setText(df.format(ubicacion.getFechaFin()));
            }

            txtFechaFin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar calen = Calendar.getInstance();
                    calen.setTime(ubicacion.getFechaFin());
                    DatePickerDialog dialogo = new DatePickerDialog(ficha_ubicacion.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int y, int m, int d) {
                            Calendar nuevaFecha = Calendar.getInstance();
                            nuevaFecha.set(y, m, d);
                            ubicacion.setFechaFin(nuevaFecha.getTime());
                            txtFechaFin.setText(df.format(nuevaFecha.getTime()));
                        }
                    }, calen.get(Calendar.YEAR), calen.get(Calendar.MONTH), calen.get(Calendar.DAY_OF_MONTH));
                    dialogo.show();
                }
            });

            Button botonEliminar = findViewById(R.id.botonEliminar);
            botonEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!ubicacion.getDescripcion().isEmpty()) {

                        Intent eliminar = new Intent(view.getContext(), activityUbicacion.class);
                        Toast.makeText(view.getContext(), "Ubicación eliminada correctamente", Toast.LENGTH_SHORT).show();
                        ubicacionHelper.borrarUbicacion(ubicacion.getCodigoUbicacion());
                        startActivity(eliminar);
                    } else {
                        Toast.makeText(view.getContext(), "No se puede eliminar la ubicación", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
