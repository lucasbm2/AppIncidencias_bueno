package com.example.usuario;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appincidencias.R;
import com.example.rol.RolDatabaseHelper;

import java.util.ArrayList;
import java.util.Collections;

import gestionincidencias.entidades.EntRol;
import gestionincidencias.entidades.EntUsuario;

public class ficha_usuario extends AppCompatActivity {

    private EntUsuario usuario;
    private UsuarioDatabaseHelper usuarioHelper;
    private ArrayList<EntRol> arRoles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha_usuario);

        UsuarioDatabaseHelper udh = new UsuarioDatabaseHelper(this, "BBDDIncidencias", null, 1);
        RolDatabaseHelper rdh = new RolDatabaseHelper(this, "BBDDIncidencias", null, 1);
        arRoles = rdh.getRoles();

        int codUsuario = getIntent().getExtras() != null ? getIntent().getExtras().getInt("codigoUsuario", -1) : -1;
        usuarioHelper = new UsuarioDatabaseHelper(this, "BBDDIncidencias", null, 1);
        usuario = (codUsuario > 0) ? udh.getUsuario(codUsuario) : new EntUsuario(0, "", "", "", "", 0);

        // Asegurar que el usuario tiene un rol antes de asignarlo al Spinner
        if (usuario.getRol() > 0) {
            for (EntRol rol : arRoles) {
                if (rol.getCodigo() == usuario.getRol()) {
                    usuario.setEntRol(rol);
                    break;
                }
            }
        }

        // Crear la lista de nombres de roles para el Spinner
        ArrayList<String> listaRoles = new ArrayList<>();
        for (EntRol rol : arRoles) {
            listaRoles.add(rol.getNombre());
        }
        Collections.sort(listaRoles);

        // Configurar el Spinner con un ArrayAdapter<String>
        Spinner spinnerRol = findViewById(R.id.spinnerRolFichaUsuario);
        ArrayAdapter<String> adapterRol = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, listaRoles);
        spinnerRol.setAdapter(adapterRol);

        // Seleccionar el rol correcto en el Spinner
        if (usuario.getEntRol() != null) {
            String nombreRolUsuario = usuario.getEntRol().getNombre();
            int index = -1;

            // Buscar el índice correcto en la lista de roles
            for (int i = 0; i < listaRoles.size(); i++) {
                if (listaRoles.get(i).equalsIgnoreCase(nombreRolUsuario)) {
                    index = i;
                    break;
                }
            }

            // Si se encontró el índice, seleccionarlo en el Spinner
            if (index != -1) {
                spinnerRol.setSelection(index);
            } else {
                Log.e("FichaUsuario", "El rol del usuario no se encuentra en la lista de roles.");
            }
        } else {
            Log.e("FichaUsuario", "El usuario no tiene un rol asignado.");
        }

        // Asignar valores a los TextView y EditText
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

        // BOTÓN PARA GUARDAR
        Button botonGuardar = findViewById(R.id.botonGuardar);
        botonGuardar.setOnClickListener(view -> {
            if (usuario != null) {
                // Obtener referencias de los campos de texto
                TextView tCodUsuario = findViewById(R.id.codigoUsuario);
                EditText tNombreUsuario = findViewById(R.id.nombreUsuario);
                EditText tCorreoUsuario = findViewById(R.id.correoUsuario);
                EditText tTelefonoUsuario = findViewById(R.id.telefonoUsuario);
                EditText tPasswordUsuario = findViewById(R.id.passwordUsuario);
                Spinner spinnerRol1 = findViewById(R.id.spinnerRolFichaUsuario);

                // Asignar datos al usuario
                usuario.setCodigoUsuario(Integer.parseInt(tCodUsuario.getText().toString()));
                usuario.setNombre(tNombreUsuario.getText().toString());
                usuario.setCorreo(tCorreoUsuario.getText().toString());
                usuario.setTelefono(tTelefonoUsuario.getText().toString());
                usuario.setPassword(tPasswordUsuario.getText().toString());

                // Obtener el nombre del rol seleccionado
                String nombreRolSeleccionado = (String) spinnerRol1.getSelectedItem();

                // Buscar el EntRol correspondiente y asignarlo al usuario
                for (EntRol rol : arRoles) {
                    if (rol.getNombre().equals(nombreRolSeleccionado)) {
                        usuario.setEntRol(rol);
                        usuario.setRol(rol.getCodigo()); // Guardamos el código del rol correctamente
                        break;
                    }
                }

                // Guardar en la base de datos
                if (usuario.getCodigoUsuario() > 0) {
                    usuarioHelper.actualizarUsuario(usuario);
                } else {
                    long id = usuarioHelper.crearUsuario(usuario);
                    usuario.setCodigoUsuario((int) id);
                }

                Toast.makeText(ficha_usuario.this, "Usuario Guardado", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getBaseContext(), activityUsuario.class));
            }
        });

        // BOTÓN PARA VOLVER
        Button botonSalir = findViewById(R.id.botonSalir);
        botonSalir.setOnClickListener(v -> {
            Intent intentSalir = new Intent(ficha_usuario.this, activityUsuario.class);
            startActivity(intentSalir);
        });

        // BOTÓN PARA ELIMINAR
        Button botonEliminar = findViewById(R.id.botonEliminar);
        botonEliminar.setOnClickListener(view -> {
            if (!usuario.getNombre().isEmpty()) {
                usuarioHelper.borrarUsuario(usuario.getCodigoUsuario());
                Toast.makeText(view.getContext(), "Usuario eliminado correctamente", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(view.getContext(), activityUsuario.class));
            } else {
                Toast.makeText(view.getContext(), "No se puede eliminar un usuario sin nombre", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
