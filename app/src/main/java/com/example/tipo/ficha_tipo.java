package com.example.tipo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appincidencias.R;
import gestionincidencias.entidades.EntTipo;

public class ficha_tipo extends AppCompatActivity {

    private EntTipo tipo;
    private TipoDatabaseHelper tipoHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha_tipo);

        tipoHelper = new TipoDatabaseHelper(this, "BBDDIncidencias", null, 1);

        int codigoTipo = getIntent().getExtras().getInt("codigoTipo");

        if (codigoTipo > 0) {
            tipo = tipoHelper.getTipo(codigoTipo);

        } else if (codigoTipo == 0) {
            tipo = new EntTipo(0, "", "");
        }

        if (tipo != null) {
            TextView txCodigo = findViewById(R.id.codigoTipo);
            txCodigo.setText(String.valueOf(tipo.getCodigoTipo()));

            TextView txNombre = findViewById(R.id.nombreTipo);
            txNombre.setText(String.valueOf(tipo.getNombre()));

            TextView txDescripcion = findViewById(R.id.descripcionTipo);
            txDescripcion.setText(String.valueOf(tipo.getDescripcion()));
        }

        // Botón para volver a la lista de ubicaciones
        Button botonVolver = findViewById(R.id.botonSalir);
        botonVolver.setOnClickListener(view -> {
            Intent intent = new Intent(ficha_tipo.this, activityTipo.class);
            startActivity(intent);
        });

        //Aqui creo el boton para guardar y su funcionamiento
        Button botonGuardar = findViewById(R.id.botonGuardar);
        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tipo != null) {

                    TextView txCodigo = findViewById(R.id.codigoTipo);
                    EditText txNombre = findViewById(R.id.nombreTipo);
                    EditText txDescripcion = findViewById(R.id.descripcionTipo);

                    tipo.setCodigoTipo(Integer.parseInt(txCodigo.getText().toString()));
                    tipo.setNombre(txNombre.getText().toString());
                    tipo.setDescripcion(txDescripcion.getText().toString());

                    //MODIFICO TIPO
                    if (tipo.getCodigoTipo() > 0) {
                        tipoHelper.actualizarTipo(tipo);
                    }
                    //AÑADO NUEVO TIPO
                    else if (tipo.getCodigoTipo() == 0) {
                        tipoHelper.crearTipo(tipo);
                    }

                    Toast toast = Toast.makeText(getApplicationContext(), "Tipo guardado correctamente", Toast.LENGTH_SHORT);
                    toast.show();

                }
                Intent intent = new Intent(ficha_tipo.this, activityTipo.class);
                startActivity(intent);
            }
        });

        Button botonEliminar = findViewById(R.id.botonEliminar);
        botonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tipo.getNombre().isEmpty()) {

                    Intent eliminar = new Intent(view.getContext(), activityTipo.class);
                    Toast.makeText(view.getContext(), "Tipo eliminado correctamente", Toast.LENGTH_SHORT).show();
                    tipoHelper.borrarTipo(tipo.getCodigoTipo());
                    startActivity(eliminar);
                } else {
                    Toast.makeText(view.getContext(), "No se puede eliminar un tipo sin nombre", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
