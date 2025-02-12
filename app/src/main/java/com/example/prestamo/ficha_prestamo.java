package com.example.prestamo;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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
import gestionincidencias.entidades.EntPrestamo;
import gestionincidencias.entidades.EntUsuario;

public class ficha_prestamo extends AppCompatActivity {

    private EntPrestamo prestamo;
    private PrestamoDatabaseHelper prestamoHelper;
    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha_prestamo);

        ElementoDBHelper edh = new ElementoDBHelper(this, "BBDDIncidencias", null, 1);
        ArrayList<EntElemento> arElementos = edh.getElementos();

        UsuarioDatabaseHelper udh = new UsuarioDatabaseHelper(this, "BBDDIncidencias", null, 1);
        ArrayList<EntUsuario> arUsuarios = udh.getUsuarios();


        int codigoPrestamo = getIntent().getExtras().getInt("codigoPrestamo");

        prestamoHelper = new PrestamoDatabaseHelper(this, "BBDDIncidencias", null, 1);


        if (codigoPrestamo > 0) {
            prestamo = prestamoHelper.getPrestamo(codigoPrestamo);
        } else if (codigoPrestamo == 0) {
            prestamo = new EntPrestamo(0, 0, 0, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
        }

        for (EntElemento ele : arElementos) {
            if (ele.getCodigoElemento() == prestamo.getIdElemento()) {
                prestamo.setElemento(ele);
                break;
            }
        }

        for (EntUsuario usu : arUsuarios) {
            if (prestamo.getIdUsuario() == usu.getCodigoUsuario()) {
                prestamo.setUsuario(usu);
                break;
            }
        }


        if (prestamo != null) {

            TextView txCodigoPrestamo = findViewById(R.id.codigoPrestamo);
            txCodigoPrestamo.setText(String.valueOf(prestamo.getCodigoPrestamo()));


            ArrayList<String> usuarios = new ArrayList<>();
            for (EntUsuario usu : arUsuarios) {
                usuarios.add(usu.getNombre());
            }

            Collections.sort(usuarios);

            Spinner spinnerUsuario = findViewById(R.id.spinnerNombreUsuarioFichaPrestamo);
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, usuarios);
            spinnerUsuario.setAdapter(spinnerArrayAdapter);

            if (prestamo != null && prestamo.getUsuario() != null) {
                spinnerUsuario.setSelection(usuarios.indexOf(prestamo.getUsuario().getNombre()));
            }

            //////
            ArrayList<String> elementos = new ArrayList<>();
            for (EntElemento ele : arElementos) {
                elementos.add(ele.getNombre());
            }
            Collections.sort(elementos);

            Spinner spinnerElemento = findViewById(R.id.spinnerNombreElementoFichaPrestamo);
            ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, elementos);

            spinnerElemento.setAdapter(spinnerArrayAdapter2);
            if (prestamo.getElemento() != null) {
                spinnerElemento.setSelection(elementos.indexOf(prestamo.getElemento().getNombre()));
            }


            /////////
            //CONTROLO QUE LAS FECHAS NO SEAN NULAS
            TextView txFechaInicio = findViewById(R.id.fechaInicioPrestamo);
            if (prestamo.getFechaInicio() != null) {
                txFechaInicio.setText(df.format(prestamo.getFechaInicio()));
            } else {
                txFechaInicio.setText("Fecha no disponible");
            }

            TextView txFechaFin = findViewById(R.id.fechaFinPrestamo);
            if (prestamo.getFechaFin() != null) {
                txFechaFin.setText(df.format(prestamo.getFechaFin()));
            } else {
                txFechaFin.setText("Fecha no disponible");
            }

        }


        ///
        Button botonVolver = findViewById(R.id.botonSalir);
        botonVolver.setOnClickListener(view -> {
            Intent intent = new Intent(ficha_prestamo.this, activityPrestamo.class);
            startActivity(intent);
        });

        //////

        Button botonGuardar = findViewById(R.id.botonGuardar);
        ArrayList<EntUsuario> finalArUsuarios = arUsuarios;
        ArrayList<EntElemento> finalArElementos = arElementos;
        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            //Metodo para guardar la ubicacion con los datos introducidos
            public void onClick(View view) {
                if (prestamo != null) {

                    TextView txtCodigo = findViewById(R.id.codigoPrestamo);

                    Spinner spinnerUsuario = findViewById(R.id.spinnerNombreUsuarioFichaPrestamo);
                    Object usuarioSeleccionado = spinnerUsuario.getSelectedItem().toString();

                    Spinner spinnerElemento = findViewById(R.id.spinnerNombreElementoFichaPrestamo);
                    Object elementoSeleccionado = spinnerElemento.getSelectedItem().toString();

                    TextView txtFechaInicio = findViewById(R.id.fechaInicioPrestamo);
                    String fechaIni = txtFechaInicio.getText().toString();
                    SimpleDateFormat formatterIni = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

                    TextView txtFechaFin = findViewById(R.id.fechaFinPrestamo);
                    String fechaFin = txtFechaFin.getText().toString();
                    SimpleDateFormat formatterFin = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());


                    for (EntUsuario usu : finalArUsuarios) {
                        if (usu.getNombre().equals(usuarioSeleccionado)) {
                            prestamo.setIdUsuario(usu.getCodigoUsuario());
                            prestamo.setUsuario(usu);
                            break;

                        }
                    }

                    for (EntElemento ele : finalArElementos) {
                        if (ele.getNombre().equals(elementoSeleccionado)) {
                            prestamo.setIdElemento(ele.getCodigoElemento());
                            prestamo.setElemento(ele);
                            break;
                        }
                    }


                    try {
                        Date fechaInicio = formatterIni.parse(fechaIni);
                        prestamo.setFechaInicio(fechaInicio);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    try {
                        Date fechaFinal = formatterFin.parse(fechaFin);
                        prestamo.setFechaFin(fechaFinal);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if (prestamo.getCodigoPrestamo() > 0) {
                        prestamoHelper.actualizarPrestamo(prestamo);
                    } else if (prestamo.getCodigoPrestamo() == 0) {
                        prestamoHelper.crearPrestamo(prestamo);
                    }

                    Toast toast = Toast.makeText(ficha_prestamo.this, "Prestamo Guardado", Toast.LENGTH_SHORT);
                    toast.show();

                }

                Intent intent = new Intent(ficha_prestamo.this, activityPrestamo.class);

                startActivity(intent);
            }
        });

        TextView txtFechaInicio = findViewById(R.id.fechaInicioPrestamo);
        txtFechaInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calen = Calendar.getInstance();
                calen.setTime(prestamo.getFechaInicio());
                DatePickerDialog dialogo = new DatePickerDialog(ficha_prestamo.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int y, int m, int d) {
                        TextView txtFechaInicio = findViewById(R.id.fechaInicioPrestamo);
                        Date fechaInicio = prestamo.getFechaInicio();
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                        String[] fecha = formatter.format(fechaInicio).split(" ");
                        txtFechaInicio.setText(y + "-" + (m + 1) + "-" + d + " " + fecha[1]);

                        TextView txtFechaFin = findViewById(R.id.fechaFinPrestamo);
                        Date fechaFin = prestamo.getFechaFin();
                        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                        String[] fecha2 = formatter2.format(fechaFin).split(" ");
                        txtFechaFin.setText(y + "-" + (m + 1) + "-" + d + " " + fecha2[1]);
                    }
                }, calen.get(Calendar.YEAR), calen.get(Calendar.MONTH), calen.get(Calendar.DAY_OF_MONTH));
                dialogo.show();
            }
        });

        TextView txtFechaFin = findViewById(R.id.fechaFinPrestamo);

        if (prestamo.getFechaFin() != null) {
            txtFechaFin.setText(df.format(prestamo.getFechaFin()));
        } else {
            prestamo.setFechaFin(new Date());
            txtFechaFin.setText(df.format(prestamo.getFechaFin()));
        }

        txtFechaFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calen = Calendar.getInstance();
                calen.setTime(prestamo.getFechaFin());

                DatePickerDialog dialogo = new DatePickerDialog(ficha_prestamo.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int y, int m, int d) {
                        Calendar nuevaFecha = Calendar.getInstance();
                        nuevaFecha.set(y, m, d);
                        prestamo.setFechaFin(nuevaFecha.getTime());
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
                if (!prestamo.getElemento().getNombre().isEmpty()) {

                    Intent eliminar = new Intent(view.getContext(), activityPrestamo.class);
                    Toast.makeText(view.getContext(), "Prestamo eliminado correctamente", Toast.LENGTH_SHORT).show();
                    prestamoHelper.borrarPrestamo(prestamo.getCodigoPrestamo());
                    startActivity(eliminar);
                } else {
                    Toast.makeText(view.getContext(), "No se puede eliminar un prestamo sin elemento", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
