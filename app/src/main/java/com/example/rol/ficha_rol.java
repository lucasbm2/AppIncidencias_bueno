package com.example.rol;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appincidencias.R;
import gestionincidencias.entidades.EntRol;

public class ficha_rol extends AppCompatActivity {

    private EntRol rol;
    //DECLARO MI ROLDATABASEHELPER
    private RolDatabaseHelper rolHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha_roles);
        //INICIALIZO MI ROLDATABASEHELPER
        rolHelper = new RolDatabaseHelper(this, "BBDDIncidencias", null, 1);

        //EXTRAEMOS LOS DATOS DE LA LISTA
        int codRol = getIntent().getExtras().getInt("codigoRol");

        //PARA MODIFICAR
        if (codRol > 0) {
            rol = rolHelper.getRol(codRol);
        }
        //NUEVO ROL
        else if (codRol == 0) {
            rol = new EntRol(0, "", "", 0);
        }

        //RECOJO DATOS DEL XML
        if (rol != null) {
            TextView txCodigoRol = findViewById(R.id.codigoRol);
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
                    TextView txCodigoRol = findViewById(R.id.codigoRol);
                    EditText txNombre = findViewById(R.id.nombreRol);
                    EditText txDescripcion = findViewById(R.id.descripcionRol);
                    EditText txNivelAcceso = findViewById(R.id.nivelAccesoRol);

                    String nombre = txNombre.getText().toString().trim();
                    String descripcion = txDescripcion.getText().toString().trim();
                    String nivelAccesoStr = txNivelAcceso.getText().toString().trim();

                    int nivelAcceso;
                    try {
                        nivelAcceso = Integer.parseInt(nivelAccesoStr);
                    } catch (NumberFormatException e) {
                        Toast.makeText(getApplicationContext(), "Nivel de acceso no válido", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    rol.setNombre(nombre);
                    rol.setDescripcion(descripcion);
                    rol.setNivel_acceso(nivelAcceso);

                    // Guardar o actualizar el rol en la base de datos
                    if (rol.getCodigo() > 0) {
                        rolHelper.actualizarRol(rol);
                    } else if (rol.getCodigo() == 0) {
                        rolHelper.crearRol(rol);
                    }
                }

                Toast toast = Toast.makeText(getApplicationContext(), "Rol guardado correctamente", Toast.LENGTH_SHORT);
                toast.show();

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
                    rolHelper.borrarRol(rol.getCodigo());
                    startActivity(eliminar);
                } else {
                    Toast.makeText(view.getContext(), "No se puede eliminar el rol", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
