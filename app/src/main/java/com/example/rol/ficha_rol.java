package com.example.rol;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appincidencias.R;
import com.example.sala.activitySalas;

import gestionincidencias.GestionIncidencias;
import gestionincidencias.entidades.EntRol;
import gestionincidencias.entidades.EntSala;

public class ficha_rol extends AppCompatActivity {

    private EntRol rol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha_roles);

        //EXTRAEMOS LOS DATOS DE LA LISTA
        int codRol = getIntent().getExtras().getInt("codigoRol");
        String nombreRol = getIntent().getExtras().getString("nombreRol");
        String descripcionRol = getIntent().getExtras().getString("descripcionRol");
        String nivelAccesoRol = getIntent().getExtras().getString("nivelAccesoRol");

        //PARA MODIFICAR
        if (codRol > 0) {
            for (EntRol s  : GestionIncidencias.getArRoles()) {
                if (s.getCodigo() == codRol) {
                    rol = s;
                }
            }
            //NUEVO ROL
        } else if (codRol == 0 && (nombreRol == null || nombreRol.isEmpty())) {
            rol = new EntRol(0, "", "", 0);

            //POR SI EL CODIGO ES 0
        } else if (codRol == 0) {
            for (EntRol s : GestionIncidencias.getArRoles()) {
                if (s.getCodigo() == codRol) {
                    rol = s;
                }
            }
        }

        //RECOJO DATOS DEL XML
        if (rol != null) {
            EditText txCodigoRol = findViewById(R.id.codigoRol);
            txCodigoRol.setText(String.valueOf(rol.getCodigo()));

            EditText txNombre = findViewById(R.id.nombreRol);
            txNombre.setText(String.valueOf(rol.getNombre()));

            EditText txDescripcion = findViewById(R.id.descripcionRol);
            txDescripcion.setText(String.valueOf(rol.getDescripcion()));

            EditText txNivelAcceso = findViewById(R.id.nivelAccesoRol);
            txNivelAcceso.setText(String.valueOf(rol.getNivel_acceso()));

        }

        Button botonVolver = findViewById(R.id.botonSalir);
        botonVolver.setOnClickListener(view -> {
            Intent intent = new Intent(ficha_rol.this, activityRoles.class);
            startActivity(intent);
        });

        //BOTON PARA GUARDAR
        Button botonGuardar = findViewById(R.id.botonGuardar);
        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rol != null) {
                    EditText txCodigoRol = findViewById(R.id.codigoRol);
                    EditText txNombre = findViewById(R.id.nombreRol);
                    EditText txDescripcion = findViewById(R.id.descripcionRol);
                    EditText txNivelAcceso = findViewById(R.id.nivelAccesoRol);

                    String nombre = txNombre.getText().toString().trim();
                    String descripcion = txDescripcion.getText().toString().trim();
                    String nivelAccesoStr = txNivelAcceso.getText().toString().trim();

                    if (nombre.isEmpty() || descripcion.isEmpty() || nivelAccesoStr.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    int nivelAcceso;
                    try {
                        nivelAcceso = Integer.parseInt(nivelAccesoStr);
                    } catch (NumberFormatException e) {
                        Toast.makeText(getApplicationContext(), "Nivel de acceso no válido", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (rol.getCodigo() != 0) {
                        rol.setCodigo(Integer.parseInt(txCodigoRol.getText().toString()));
                        rol.setNombre(nombre);
                        rol.setDescripcion(descripcion);
                        rol.setNivel_acceso(nivelAcceso);
                    } else if (rol.getCodigo() == 0 && rol.getNombre().isEmpty()) {
                        boolean encontrado = false;
                        for (EntRol s : GestionIncidencias.getArRoles()) {
                            if (s.getCodigo() == rol.getCodigo()) {
                                encontrado = true;
                                break;
                            }
                        }
                        if (!encontrado) {
                            int siguienteCodigo = 1;
                            for (EntRol s : GestionIncidencias.getArRoles()) {
                                if (s.getCodigo() >= siguienteCodigo) {
                                    siguienteCodigo = s.getCodigo() + 1;
                                }
                            }
                            rol.setCodigo(siguienteCodigo);
                            rol.setNombre(nombre);
                            rol.setDescripcion(descripcion);
                            rol.setNivel_acceso(nivelAcceso);
                        }
                        GestionIncidencias.getArRoles().add(GestionIncidencias.getArRoles().size(), rol);
                    } else if (rol.getCodigo() == 0) {
                        rol.setCodigo(GestionIncidencias.getArRoles().size() + 1);
                        rol.setNombre(nombre);
                        rol.setDescripcion(descripcion);
                        rol.setNivel_acceso(nivelAcceso);
                    }
                }

                Toast.makeText(getApplicationContext(), "Rol guardado correctamente", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ficha_rol.this, activityRoles.class);
                startActivity(intent);
            }
        });



        Button botonEliminar = findViewById(R.id.botonEliminar);
        botonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!rol.getNombre().isEmpty()) {

                    Intent eliminar = new Intent(view.getContext(), activityRoles.class);
                    Toast.makeText(view.getContext(), "Rol eliminado correctamente", Toast.LENGTH_SHORT).show();
                    GestionIncidencias.getArRoles().remove(rol);
                    startActivity(eliminar);
                } else {
                    Toast.makeText(view.getContext(), "No se puede eliminar el rol", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
