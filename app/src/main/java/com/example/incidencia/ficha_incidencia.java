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
import com.example.ubicacion.activityUbicacion;
import com.example.ubicacion.ficha_ubicacion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

import gestionincidencias.GestionIncidencias;
import gestionincidencias.entidades.EntElemento;
import gestionincidencias.entidades.EntIncidencia;
import gestionincidencias.entidades.EntUsuario;

public class ficha_incidencia extends AppCompatActivity {

    //Creo una incidencia
    EntIncidencia incidencia;
    //Este es el formato a aplicar a las fechas despues
    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha_incidencia);

        int codIncidencia = getIntent().getExtras().getInt("codigoIncidencia");
        String descripcionIncidencia = getIntent().getExtras().getString("descripcionIncidencia");

        //PARA MODIFICAR LA INCIDENCIA
        if (codIncidencia > 0) {
            for (EntIncidencia incic : GestionIncidencias.getArIncidencias()) {
                //Recorro EntIncidencia y si coincide el codigo lo asigno a incidencia
                if (incic.getCodigoIncidencia() == codIncidencia) {
                    incidencia = incic;
                }
            }
        }

        //AÑADIR UNA NUEVA INCIDENCIA
        else if (codIncidencia == 0 && descripcionIncidencia.isEmpty()) {
            incidencia = new EntIncidencia(0, "", 0, new Date(System.currentTimeMillis()), 0);
        }
        //POR SI LA NUEVA UBICACION TIENE CODIGO 0
        else if (codIncidencia == 0) {
            for (EntIncidencia inci : GestionIncidencias.getArIncidencias()) {
                if (inci.getCodigoIncidencia() == codIncidencia) {
                    incidencia = inci;
                }
            }
        }

        //RECOJO LOS DATOS DEL XML
        if (incidencia != null) {
            TextView txcodIncidencia = findViewById(R.id.codigoIncidencia);
            txcodIncidencia.setText(String.valueOf(incidencia.getCodigoIncidencia()));

            TextView txDescripcion = findViewById(R.id.descripcionIncidencia);
            txDescripcion.setText(incidencia.getDescripcion());


            //AQUI ASIGNO LOS VALORES QUE SE VAN A MOSTRAR EN EL SPINNER
            ArrayList<String> usuarios = new ArrayList<>();
            for (EntUsuario usu : GestionIncidencias.getArUsuarios()) {
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
            for (EntElemento ele : GestionIncidencias.getArElementos()) {
                //Para controlar que no repita varias veces las variables en el spinner
                if (!elementos.contains(ele.getTipoElemento().getNombre())) {
                    elementos.add(String.valueOf(ele.getTipoElemento().getNombre()));
                }
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
                spinnerElemento.setSelection(elementos.indexOf(incidencia.getElemento().getDescripcion()));
            }


            //CONTROLO QUE LAS FECHAS NO SEAN NULAS
            TextView txFechaCreacion = findViewById(R.id.editarFechaCreacionUsuarioIncidencia);
            if (incidencia.getFechaCreacion() != null) {
                txFechaCreacion.setText(df.format(incidencia.getFechaCreacion()));
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

                    TextView txtCodigo = findViewById(R.id.codigoIncidencia);
                    EditText txtDescripcion = findViewById(R.id.descripcionIncidencia);

                    Spinner spinnerUsuario = findViewById(R.id.spinnerUsuarioCreacionFichaIncidencia);
                    Object usuarioSeleccionado = spinnerUsuario.getSelectedItem().toString();

                    Spinner spinnerElemento = findViewById(R.id.spinnerElementoFichaIncidencia);
                    Object elementoSeleccionado = spinnerElemento.getSelectedItem().toString();

                    TextView txtFechaCreacion = findViewById(R.id.editarFechaCreacionUsuarioIncidencia);
                    String fechaCreacion = txtFechaCreacion.getText().toString();
                    SimpleDateFormat formatterIni = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());



                    //PARA MODIFICAR LOS DATOS DE ALGUN ELEMENTO QUE YA EXISTE
                    if (incidencia.getCodigoIncidencia() != 0) {
                        incidencia.setCodigoIncidencia(Integer.parseInt(txtCodigo.getText().toString()));
                        incidencia.setDescripcion(txtDescripcion.getText().toString());

                        for (EntUsuario usu : GestionIncidencias.getArUsuarios()) {
                            if (usu.getNombre().equals(spinnerUsuario.getSelectedItem().toString())) {
                                incidencia.setIdUsuarioCreacion(usu.getCodigoUsuario());
                                incidencia.setUsuarioCreacion(usu);
                                break;
                            }
                        }

                        for (EntElemento elem : GestionIncidencias.getArElementos()) {
                            if (elem.getNombre().equals(spinnerElemento.getSelectedItem().toString())) {
                                incidencia.setIdElemento(elem.getCodigoElemento());
                                incidencia.setElemento(elem);
                                break;
                            }
                        }

                        try {
                            Date fechaCreacionDate = formatterIni.parse(fechaCreacion);
                            incidencia.setFechaCreacion(fechaCreacionDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    } else if (incidencia.getCodigoIncidencia() == 0 && incidencia.getDescripcion().isEmpty()) {
                        boolean encontrado = false;

                        for (EntIncidencia inci : GestionIncidencias.getArIncidencias()) {
                            if (inci.getCodigoIncidencia() == incidencia.getCodigoIncidencia()) {
                                encontrado = true;
                                break;
                            }
                        }
                        if (!encontrado) {
                            int siguienteCodigo = 1;
                            for (EntIncidencia inci : GestionIncidencias.getArIncidencias()) {
                                if (inci.getCodigoIncidencia() >= siguienteCodigo) {
                                    siguienteCodigo = inci.getCodigoIncidencia() + 1;
                                }
                            }
                            incidencia.setCodigoIncidencia(siguienteCodigo);
                            incidencia.setDescripcion(txtDescripcion.getText().toString());


                            for (EntUsuario usu : GestionIncidencias.getArUsuarios()) {
                                if (usu.getNombre().equals(spinnerUsuario.getSelectedItem().toString())) {
                                    incidencia.setIdUsuarioCreacion(usu.getCodigoUsuario());
                                    incidencia.setUsuarioCreacion(usu);
                                    break;
                                }
                            }

                            for (EntElemento elem : GestionIncidencias.getArElementos()) {
                                if (elem.getNombre().equals(spinnerElemento.getSelectedItem().toString())) {
                                    incidencia.setIdElemento(elem.getCodigoElemento());
                                    incidencia.setElemento(elem);
                                    break;
                                }
                            }

                            try {
                                Date fechaCreacionDate = formatterIni.parse(fechaCreacion);
                                incidencia.setFechaCreacion(fechaCreacionDate);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        GestionIncidencias.getArIncidencias().remove(incidencia);
                        GestionIncidencias.getArIncidencias().add(GestionIncidencias.getArIncidencias().size(), incidencia);
                    } else if (incidencia.getCodigoIncidencia() == 0) {

                        incidencia.setCodigoIncidencia(Integer.parseInt(txtCodigo.getText().toString()));
                        incidencia.setDescripcion(txtDescripcion.getText().toString());


                        for (EntUsuario usu : GestionIncidencias.getArUsuarios()) {
                            if (usu.getNombre().equals(usuarioSeleccionado)) {
                                incidencia.setIdUsuarioCreacion(usu.getCodigoUsuario());
                                incidencia.setUsuarioCreacion(usu);
                                break;
                            }
                        }

                        for (EntElemento elem : GestionIncidencias.getArElementos()) {
                            if (elem.getNombre().equals(spinnerElemento.getSelectedItem().toString())) {
                                incidencia.setIdElemento(elem.getCodigoElemento());
                                incidencia.setElemento(elem);
                                break;
                            }
                        }

                        try {
                            Date fechaCreacionDate = formatterIni.parse(String.valueOf(txtFechaCreacion));
                            incidencia.setFechaCreacion(fechaCreacionDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }


                }

                Toast toast = Toast.makeText(getApplicationContext(), "Incidencia guardada correctamente", Toast.LENGTH_SHORT);
                toast.show();

                Intent intentIncidencia = new Intent(   ficha_incidencia.this, activityIncidencia.class);
                startActivity(intentIncidencia);
            }
        });

        TextView txFechaCreacion = findViewById(R.id.editarFechaCreacionUsuarioIncidencia);
        txFechaCreacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calen = Calendar.getInstance();
                calen.setTime(incidencia.getFechaCreacion());

                DatePickerDialog dialogo = new DatePickerDialog(ficha_incidencia.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int y, int m, int d) {
                        Calendar nuevaFecha = Calendar.getInstance();
                        nuevaFecha.set(y, m, d);
                        incidencia.setFechaCreacion(nuevaFecha.getTime());
                        txFechaCreacion.setText(df.format(nuevaFecha.getTime()));
                    }
                }, calen.get(Calendar.YEAR), calen.get(Calendar.MONTH), calen.get(Calendar.DAY_OF_MONTH));

                dialogo.show();
            }
        });


    }
}
