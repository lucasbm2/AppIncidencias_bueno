package com.example.incidencia;

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
import com.example.usuario.UsuarioDatabaseHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

import gestionincidencias.entidades.EntElemento;
import gestionincidencias.entidades.EntIncidencia;
import gestionincidencias.entidades.EntUsuario;

public class ficha_incidencia extends AppCompatActivity {

    //Creo una incidencia
    EntIncidencia incidencia;
    private IncidenciaDatabaseHelper incidenciaHelper;
    //Este es el formato a aplicar a las fechas despues
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha_incidencia);
        incidenciaHelper = new IncidenciaDatabaseHelper(this, "BBDDIncidencias", null, 1);

        // Ahora ya no es null y podemos usarlo
        ArrayList<EntIncidencia> arIncidencias = incidenciaHelper.getIncidencias();
        AdaptadorIncidencia adaptadorIncidencia = new AdaptadorIncidencia(this, arIncidencias.toArray(new EntIncidencia[0]));

        ElementoDBHelper edh = new ElementoDBHelper(this, "BBDDIncidencias", null, 1);
        ArrayList<EntElemento> arElementos = edh.getElementos();

        UsuarioDatabaseHelper udh = new UsuarioDatabaseHelper(this, "BBDDIncidencias", null, 1);
        ArrayList<EntUsuario> arUsuarios = udh.getUsuarios();

        int codIncidencia = getIntent().getExtras().getInt("codigoIncidencia");


        if (codIncidencia > 0) {
            incidencia = incidenciaHelper.getIncidencia(codIncidencia);
        }
        else if (codIncidencia == 0) {
            incidencia = new EntIncidencia(0, "", 0, new Date(System.currentTimeMillis()), 0);
        }

        for (EntElemento ele : arElementos) {
            if (ele.getCodigoElemento() == incidencia.getIdElemento()) {
                incidencia.setElemento(ele);
                break;
            }
        }

        for (EntUsuario usu : arUsuarios) {
            if (usu.getCodigoUsuario() == incidencia.getIdUsuarioCreacion()) {
                incidencia.setUsuarioCreacion(usu);
                break;
            }
        }

        //RECOJO LOS DATOS DEL XML
        if (incidencia != null) {
            TextView txcodIncidencia = findViewById(R.id.codigoIncidencia);
            txcodIncidencia.setText(String.valueOf(incidencia.getCodigoIncidencia()));

            Log.d("FichaIncidencia", "Descripción de la incidencia: " + incidencia.getDescripcion());

            TextView txDescripcion = findViewById(R.id.descripcionIncidencia);
            if (incidencia.getDescripcion() != null && !incidencia.getDescripcion().isEmpty()) {
                txDescripcion.setText(incidencia.getDescripcion());
            } else {
                txDescripcion.setText("Sin descripción");
            }



            //AQUI ASIGNO LOS VALORES QUE SE VAN A MOSTRAR EN EL SPINNER
            ArrayList<String> usuarios = new ArrayList<>();
            for (EntUsuario usu : arUsuarios) {
                usuarios.add(usu.getNombre());
            }

            Collections.sort(usuarios);

            //Encuentro el spinner y lo asigno a una variable spinnerSala
            Spinner spinnerUsuario = findViewById(R.id.spinnerUsuarioCreacionFichaIncidencia);
            //Creo un adaptador para el spinner, con el contexto, tipo de spinner y la lista que hemos creado antes
            ArrayAdapter<String> spinnerArrayUsuario = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, usuarios);
            //Asigno el adaptador al spinner
            spinnerUsuario.setAdapter(spinnerArrayUsuario);
            //Compruebo que la ubicacion no sea null y que tenga sala y entonces asigno el elemento seleccionado al spinner
            if (incidencia != null && incidencia.getUsuarioCreacion() != null) {
                spinnerUsuario.setSelection(usuarios.indexOf(incidencia.getUsuarioCreacion().getNombre()));
            }
            //

            //AQUI ASIGNO LOS VALORES QUE SE VAN A MOSTRAR EN EL SPINNER
            ArrayList<String> elementos = new ArrayList<>();
            for (EntElemento ele : arElementos) {
                elementos.add(ele.getNombre());
            }

            Collections.sort(elementos);

            //Encuentro el spinner y lo asigno a una variable spinnerSala
            Spinner spinnerElemento = findViewById(R.id.spinnerElementoFichaIncidencia);
            //Creo un adaptador para el spinner, con el contexto, tipo de spinner y la lista que hemos creado antes
            ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, elementos);
            //Asigno el adaptador al spinner
            spinnerElemento.setAdapter(spinnerArrayAdapter2);
            //Compruebo que la ubicacion no sea null y que tenga sala y entonces asigno el elemento seleccionado al spinner
            if (incidencia != null && incidencia.getElemento() != null) {
                String nombreElemento = incidencia.getElemento().getNombre(); // Asegúrate de usar getNombre()
                int indexElemento = elementos.indexOf(nombreElemento);
                if (indexElemento != -1) {
                    spinnerElemento.setSelection(indexElemento);
                }
            }



            //CONTROLO QUE LAS FECHAS NO SEAN NULAS
            TextView txFechaCreacion = findViewById(R.id.editarFechaCreacionUsuarioIncidencia);
            if (incidencia.getFechaCreacion() != null) {
                txFechaCreacion.setText(df.format(incidencia.getFechaCreacion()));
                Log.d("FichaIncidencia", "Fecha Creación antes de formatear: " + incidencia.getFechaCreacion());

            } else {
                txFechaCreacion.setText("Fecha no disponible");
            }

        }
        // Botón para volver a la lista de ubicaciones
        Button botonVolver = findViewById(R.id.botonSalir);
        botonVolver.setOnClickListener(view -> {
            Intent intent = new Intent(ficha_incidencia.this, activityIncidencia.class);
            startActivity(intent);
        });


        //BOTON PARA GUARDAR LOS CAMBIOS
        Button botonGuardar = findViewById(R.id.botonGuardar);
        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (incidencia != null) {


                    Spinner spinnerUsuario = findViewById(R.id.spinnerUsuarioCreacionFichaIncidencia);
                    Object usuarioSeleccionado = spinnerUsuario.getSelectedItem().toString();

                    Spinner spinnerElemento = findViewById(R.id.spinnerElementoFichaIncidencia);
                    Object elementoSeleccionado = spinnerElemento.getSelectedItem().toString();

                    TextView txtFechaCreacion = findViewById(R.id.editarFechaCreacionUsuarioIncidencia);
                    String fechaCreacion = txtFechaCreacion.getText().toString();
                    SimpleDateFormat formatterIni = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());


                    for (EntUsuario usu : arUsuarios) {
                        if (usu.getNombre().equals(usuarioSeleccionado)) {
                            incidencia.setIdUsuarioCreacion(usu.getCodigoUsuario());
                            incidencia.setUsuarioCreacion(usu);
                            break;
                        }
                    }

                    for (EntElemento ele : arElementos) {
                        if (ele.getNombre().equals(elementoSeleccionado)) {
                            incidencia.setIdElemento(ele.getCodigoElemento());
                            incidencia.setElemento(ele);
                            break;
                        }
                    }

                    try {
                        Date fechaCreacionDate = formatterIni.parse(fechaCreacion);
                        incidencia.setFechaCreacion(fechaCreacionDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    EditText txtDescripcion = findViewById(R.id.descripcionIncidencia);
                    incidencia.setDescripcion(txtDescripcion.getText().toString());

                    if (incidencia.getCodigoIncidencia() > 0) {
                        incidenciaHelper.actualizarIncidencia(incidencia);
                    } else if (incidencia.getCodigoIncidencia() == 0) {
                        long id = incidenciaHelper.crearIncidencia(incidencia);
                        incidencia.setCodigoIncidencia((int) id);
                    }

                }

                Toast toast = Toast.makeText(getApplicationContext(), "Incidencia guardada correctamente", Toast.LENGTH_SHORT);
                toast.show();

                Intent intentIncidencia = new Intent(ficha_incidencia.this, activityIncidencia.class);
                startActivity(intentIncidencia);
            }
        });

        TextView txFechaCreacion = findViewById(R.id.editarFechaCreacionUsuarioIncidencia);
        txFechaCreacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (incidencia == null || incidencia.getFechaCreacion() == null) {
                    Log.e("FichaIncidencia", "Error: incidencia o fechaCreacion es null");
                    return;
                }

                Calendar calen = Calendar.getInstance();
                calen.setTime(incidencia.getFechaCreacion());

                DatePickerDialog dialogo = new DatePickerDialog(ficha_incidencia.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int y, int m, int d) {
                        TextView txtFechaCreacion = findViewById(R.id.editarFechaCreacionUsuarioIncidencia);
                        Date fechaCreacion = incidencia.getFechaCreacion();

                        if (txtFechaCreacion != null && fechaCreacion != null) {
                            // Mantener la hora existente
                            Calendar nuevaFecha = Calendar.getInstance();
                            nuevaFecha.setTime(fechaCreacion);
                            nuevaFecha.set(Calendar.YEAR, y);
                            nuevaFecha.set(Calendar.MONTH, m);
                            nuevaFecha.set(Calendar.DAY_OF_MONTH, d);

                            // Formatear y mostrar la nueva fecha con hora
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                            txtFechaCreacion.setText(formatter.format(nuevaFecha.getTime()));

                            // Actualizar el objeto incidencia con la nueva fecha
                            incidencia.setFechaCreacion(nuevaFecha.getTime());
                        } else {
                            Log.e("FichaIncidencia", "Error: txtFechaCreacion o fechaCreacion es null");
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
                if (!incidencia.getDescripcion().isEmpty()) {

                    Intent eliminar = new Intent(view.getContext(), activityIncidencia.class);
                    Toast.makeText(view.getContext(), "Elemento eliminado correctamente", Toast.LENGTH_SHORT).show();
                    incidenciaHelper.borrarIncidencia(incidencia.getCodigoIncidencia());
                    startActivity(eliminar);
                } else {
                    Toast.makeText(view.getContext(), "No se puede eliminar una incidencia sin descripcion", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
