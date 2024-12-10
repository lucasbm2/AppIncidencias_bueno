package com.example.usuario;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appincidencias.R;
import com.example.elemento.activityElemento;

import java.util.ArrayList;
import java.util.Collections;

import gestionincidencias.GestionIncidencias;
import gestionincidencias.entidades.EntRol;
import gestionincidencias.entidades.EntSala;
import gestionincidencias.entidades.EntUsuario;

public class ficha_usuario extends AppCompatActivity {

    //Creo un usuario
    private EntUsuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha_usuario);

        //Extraigo los datos del bundle
        int codUsuario = getIntent().getIntExtra("codigoUsuario", 0);
        String nombreUsuario = getIntent().getStringExtra("nombreUsuario");
        String rolUsuario = getIntent().getStringExtra("rolUsuario");

        //AHORA PARA MODIFICAR EL USUARIO
        if (codUsuario > 0) {
            for (EntUsuario u : GestionIncidencias.getArUsuarios()) {
                //Recorro el usuario y si coincide lo asigno
                if (u.getCodigoUsuario() == codUsuario) {
                    usuario = u;
                }
            }
        } else if (codUsuario == 0 && nombreUsuario.isEmpty()) {
            usuario = new EntUsuario(0, "", "", "", "", 0);
        } else if (codUsuario == 0) {
            for (EntUsuario u : GestionIncidencias.getArUsuarios()) {
                if (u.getCodigoUsuario() == codUsuario) {
                    usuario = u;
                }
            }
        }

        //AHORA RECOJO LOS DATOS DEL XML Y LOS ASIGNO
        if (usuario != null) {
            TextView txCodUsuario = findViewById(R.id.codigoUsuario);
            txCodUsuario.setText(String.valueOf(usuario.getCodigoUsuario()));

            EditText txNombreUsuario = findViewById(R.id.nombreUsuario);
            txNombreUsuario.setText(usuario.getNombre());

            EditText txCorreoUsuario = findViewById(R.id.correoUsuario);
            txCorreoUsuario.setText(usuario.getCorreo());

            EditText txTelefonoUsuario = findViewById(R.id.telefonoUsuario);
            txTelefonoUsuario.setText(usuario.getTelefono());

            EditText txPasswordUsuario = findViewById(R.id.passwordUsuario);
            txPasswordUsuario.setText(usuario.getPassword());

            ArrayList<String> roles = new ArrayList<>();
            for (EntRol rol : GestionIncidencias.getArRoles()) {
                roles.add(rol.getNombre());
            }
            Collections.sort(roles);

            Spinner spinnerRol = findViewById(R.id.spinnerRolFichaUsuario);
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, roles);
            spinnerRol.setAdapter(spinnerArrayAdapter);

            //Asigna el rol al spinner si el usuario ya tiene un rol
            if (usuario.getEntRol() != null) {
                int rolIndex = roles.indexOf(usuario.getEntRol().getNombre());
                if (rolIndex >= 0) {
                    spinnerRol.setSelection(rolIndex);
                }
            }
        }

        //BOTON PARA VOLVER
        Button botonSalir = findViewById(R.id.botonSalir);
        botonSalir.setOnClickListener(v -> {
            Intent intentSalir = new Intent(ficha_usuario.this, activityUsuario.class);
            startActivity(intentSalir);
        });

        //BOTON PARA GUARDAR
        Button botonGuardar = findViewById(R.id.botonGuardar);
        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (usuario != null) {

                    TextView txCodUsuario = findViewById(R.id.codigoUsuario);
                    EditText txNombreUsuario = findViewById(R.id.nombreUsuario);
                    EditText txCorreoUsuario = findViewById(R.id.correoUsuario);
                    EditText txTelefonoUsuario = findViewById(R.id.telefonoUsuario);
                    EditText txPasswordUsuario = findViewById(R.id.passwordUsuario);

                    Spinner spinnerRol = findViewById(R.id.spinnerRolFichaUsuario);
                    String rolSeleccionado = spinnerRol.getSelectedItem().toString();

                    //PARA MODIFICAR LOS DATOS
                    if (usuario.getCodigoUsuario() != 0) {
                        usuario.setCodigoUsuario(Integer.parseInt(txCodUsuario.getText().toString()));
                        usuario.setNombre(txNombreUsuario.getText().toString());
                        usuario.setCorreo(txCorreoUsuario.getText().toString());
                        usuario.setTelefono(txTelefonoUsuario.getText().toString());
                        usuario.setPassword(txPasswordUsuario.getText().toString());

                        for (EntRol rol : GestionIncidencias.getArRoles()) {
                            if (rol.getNombre().equals(rolSeleccionado)) {
                                usuario.setEntRol(rol);
                                usuario.setRol(rol.getCodigo());
                                break;
                            }
                        }

                        //PARA AÑADIR UN USUARIO NUEVO
                    } else if (usuario.getCodigoUsuario() == 0 && usuario.getNombre().isEmpty()) {
                        boolean encontrado = false;

                        //POR SI YA HAY UN USUARIO CON ESE CODIGO
                        for (EntUsuario u : GestionIncidencias.getArUsuarios()) {
                            if (u.getCodigoUsuario() == usuario.getCodigoUsuario()) {
                                encontrado = true;
                                break;
                            }
                        }

                        if (!encontrado) {
                            //PARA BUSCAR EL SIGUIENTE CODIGO DISPONIBLE
                            int siguienteCodigo = 1;
                            for (EntUsuario u : GestionIncidencias.getArUsuarios()) {
                                if (u.getCodigoUsuario() >= siguienteCodigo) {
                                    siguienteCodigo = u.getCodigoUsuario() + 1;
                                }
                            }

                            for (EntRol rol : GestionIncidencias.getArRoles()) {
                                if (rol.getNombre().equals(rolSeleccionado)) {
                                    usuario.setEntRol(rol);
                                    usuario.setRol(rol.getCodigo());
                                    break;
                                }
                            }

                            usuario.setCodigoUsuario(siguienteCodigo);
                            usuario.setNombre(txNombreUsuario.getText().toString());
                            usuario.setCorreo(txCorreoUsuario.getText().toString());
                            usuario.setTelefono(txTelefonoUsuario.getText().toString());
                            usuario.setPassword(txPasswordUsuario.getText().toString());
                        }

                        //AÑADO EL USUARIO A LA LISTA
                        GestionIncidencias.getArUsuarios().add(usuario);

                        //SI EL CODIGO USUARIO ES 0 PERO TIENE NOMBRE
                    } else if (usuario.getCodigoUsuario() == 0) {
                        for (EntRol rol : GestionIncidencias.getArRoles()) {
                            if (rol.getNombre().equals(rolSeleccionado)) {
                                usuario.setEntRol(rol);
                                usuario.setRol(rol.getCodigo());
                                break;
                            }
                        }

                        usuario.setCodigoUsuario(Integer.parseInt(txCodUsuario.getText().toString()));
                        usuario.setNombre(txNombreUsuario.getText().toString());
                        usuario.setCorreo(txCorreoUsuario.getText().toString());
                        usuario.setTelefono(txTelefonoUsuario.getText().toString());
                        usuario.setPassword(txPasswordUsuario.getText().toString());
                    }

                    Toast.makeText(ficha_usuario.this, "Usuario Guardado", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getBaseContext(), activityUsuario.class);
                    startActivity(intent);
                }
            }
        });

        //BOTON PARA ELIMINAR
        Button botonEliminar = findViewById(R.id.botonEliminar);
        botonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!usuario.getNombre().isEmpty()) {
                    Intent eliminar = new Intent(view.getContext(), activityUsuario.class);
                    Toast.makeText(view.getContext(), "Usuario eliminado correctamente", Toast.LENGTH_SHORT).show();
                    GestionIncidencias.getArUsuarios().remove(usuario);
                    startActivity(eliminar);
                } else {
                    Toast.makeText(view.getContext(), "No se puede eliminar un usuario sin nombre", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
