package com.example.ubicacion;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
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

import gestionincidencias.GestionIncidencias;
import gestionincidencias.entidades.EntElemento;
import gestionincidencias.entidades.EntSala;
import gestionincidencias.entidades.EntUbicacion;
import kotlin.UByte;

public class ficha_ubicacion extends AppCompatActivity {

    //Creo una ubicacion
    private EntUbicacion ubicacion;
    //Este es el formato a aplicar a las fechas despues
    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha_ubicacion);

        //INICIALIZO MI TIPODATABASEHELPER
        UbicacionDatabaseHelper ubicacionHelper = new UbicacionDatabaseHelper(this, "BBDDIncidencias", null, 1);

        // EXTRAEMOS LOS DATOS DE LA LISTA
        int codUbicacion = getIntent().getExtras().getInt("codigoUbicacion");

        //PARA MODIFICAR LA UBICACION
        // Buscar la ubicación si el código es mayor que 0 para saber que ya existe (AL ASIGNAR LO ASIGNO AL SIGUIENTE NUM DISPONIBLE)
        if (codUbicacion > 0) {
            try {
                ubicacion = ubicacionHelper.getUbicacion(codUbicacion);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }


            //PARA CUANDO AÑADO UNA NUEVA UBICACIÓN
        } if (codUbicacion == 0) {
            ubicacion = new EntUbicacion(0, 0, 0, "", new Date(), new Date());
        }


        //RECOJO LOS DATOS DEL XML Y LOS ASIGNO
        if (ubicacion != null) {
            TextView txcodUbicacion = findViewById(R.id.codigoUbicacionFicha);
            txcodUbicacion.setText(String.valueOf(ubicacion.getCodigoUbicacion()));

            EditText txDescripcion = findViewById(R.id.descripcionUbicacion);
            txDescripcion.setText(ubicacion.getDescripcion());

            SalaDatabaseHelper salaHelper = new SalaDatabaseHelper(this, "BBDDIncidencias", null, 1);
            ArrayList<EntSala> salas = salaHelper.getSalas();
            ArrayList<String> nombresSalas = new ArrayList<>();
            for (EntSala sala : salas) {
                if (sala != null) {
                    nombresSalas.add(sala.getNombre());
                }
            }

            Spinner spinnerSala = findViewById(R.id.spinnerIdSalaFichaUbicacion);
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nombresSalas);
            spinnerSala.setAdapter(spinnerArrayAdapter);

            if (ubicacion.getSala() != null) {
                int indice = nombresSalas.indexOf(ubicacion.getSala().getNombre());
                if (indice >= 0) {
                    spinnerSala.setSelection(indice);
                }
            }

            ElementoDBHelper elementoHelper = new ElementoDBHelper(this, "BBDDIncidencias", null, 1);
            ArrayList<EntElemento> elementos = elementoHelper.getElementos();
            ArrayList<String> nombresElementos = new ArrayList<>();
            for (EntElemento elemento : elementos) {
                if (elemento != null) {
                    nombresElementos.add(elemento.getNombre());
                }
            }

            Spinner spinnerElemento = findViewById(R.id.spinnerElementoFichaUbicacion);
            ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nombresElementos);
            spinnerElemento.setAdapter(spinnerArrayAdapter2);

            if (ubicacion.getElemento() != null) {
                int indice = nombresElementos.indexOf(ubicacion.getElemento().getNombre());
                if (indice >= 0) {
                    spinnerElemento.setSelection(indice);
                }
            }

            TextView txFechaInicio = findViewById(R.id.editarFechaInicioUbicacion);
            TextView txFechaFin = findViewById(R.id.editarFechaFinUbicacion);

            if (ubicacion != null) {
                if (ubicacion.getFechaInicio() != null) {
                    txFechaInicio.setText(df.format(ubicacion.getFechaInicio()));
                } else {
                    txFechaInicio.setText("Fecha no disponible");
                    ubicacion.setFechaInicio(new Date());
                }

                if (ubicacion.getFechaFin() != null) {
                    txFechaFin.setText(df.format(ubicacion.getFechaFin()));
                } else {
                    txFechaFin.setText("Fecha no disponible");
                    ubicacion.setFechaFin(new Date());
                }
            } else {
                txFechaInicio.setText("Ubicación no disponible");
                txFechaFin.setText("Ubicación no disponible");
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

                    //PARA MODIFICAR LOS DATOS DE ALGUN ELEMENTO QUE YA EXISTE
                    if (ubicacion.getCodigoUbicacion() > 0) {
                        ubicacionHelper.actualizarUbicacion(ubicacion);

                        //PARA ASIGNAR CUANDO AÑADO UNA NUEVA UBICACIÓN
                    } else if (ubicacion.getCodigoUbicacion() == 0) {

                        if (ubicacion.getFechaInicio() == null) {
                            ubicacion.setFechaInicio(new Date());
                        }

                        if (ubicacion.getFechaFin() == null) {
                            ubicacion.setFechaFin(new Date());
                        }
                        ubicacionHelper.actualizarUbicacion(ubicacion);
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
                if (ubicacion != null && ubicacion.getFechaInicio() != null) {
                    calen.setTime(ubicacion.getFechaInicio());
                } else {
                    calen.setTime(new Date());
                }

                DatePickerDialog dialogo = new DatePickerDialog(ficha_ubicacion.this, (view1, y, m, d) -> {
                    if (ubicacion != null) {
                        Calendar nuevaFecha = Calendar.getInstance();
                        nuevaFecha.set(y, m, d);
                        ubicacion.setFechaInicio(nuevaFecha.getTime());
                        txtFechaInicio.setText(df.format(nuevaFecha.getTime()));
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
                if (ubicacion != null && ubicacion.getFechaFin() != null) {
                    calen.setTime(ubicacion.getFechaFin());
                } else {
                    calen.setTime(new Date());
                }

                DatePickerDialog dialogo = new DatePickerDialog(ficha_ubicacion.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int y, int m, int d) {
                        if (ubicacion != null) {
                            Calendar nuevaFecha = Calendar.getInstance();
                            nuevaFecha.set(y, m, d);
                            ubicacion.setFechaFin(nuevaFecha.getTime());
                            txtFechaFin.setText(df.format(nuevaFecha.getTime()));
                        }
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
