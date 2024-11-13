package com.example.usuario;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appincidencias.R;

public class ficha_usuario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha_usuario);

        //Creamos un intent para poder obtener los datos de la otra actividad (activitySalas)
        Intent infoFichaUsuario = this.getIntent();

        //Creamos un Bundle para almacenar los datos de la otra actividad (activitySalas)
        Bundle bnd = infoFichaUsuario.getExtras();

        //Creamos variables para guardar los datos almacenados en el Bundle, usamos el getString y enre parentesis el nombre
        //que hemos puesto antes en el putExtra, Ejemplo: intenFichaSala.putExtra("sala", salaSeleccionada.getCodigoSala());
        //Esto esta sacado del OnItemClick de la activitySalas. En este caso "sala" seria el nombre
        String codUsuario = String.valueOf(bnd.getInt("codigoUsuario"));
        String nombreUsuario = bnd.getString("nombre");
        String correoUsuario = bnd.getString("correo");
        String telefonoUsuario = bnd.getString("telefono");
        String passwordUsuario = bnd.getString("password");
        String rolUsuario = bnd.getString("rol");

        //Creamos variables TextView para encontrar el elemento donde queremos mostrar los datos obtenidos.
        //Estos estan en activity_ficha_sala.xml en mi caso.
        TextView txCodUsuario = findViewById(R.id.codigoUsuario);
        txCodUsuario.setText(String.valueOf(codUsuario));
        TextView txNombreUsuario = findViewById(R.id.nombreUsuario);
        txNombreUsuario.setText(String.valueOf(nombreUsuario));
        TextView txCorreoUsuario = findViewById(R.id.correoUsuario);
        txCorreoUsuario.setText(String.valueOf(correoUsuario));
        TextView txTelefonoUsuario = findViewById(R.id.telefonoUsuario);
        txTelefonoUsuario.setText(String.valueOf(telefonoUsuario));
        TextView txPasswordUsuario = findViewById(R.id.passwordUsuario);
        txPasswordUsuario.setText(String.valueOf(passwordUsuario));
        TextView txRolUsuario = findViewById(R.id.rolUsuario);
        txRolUsuario.setText(String.valueOf(rolUsuario));

        Button botonSalir = findViewById(R.id.botonSalir);
        botonSalir.setOnClickListener(v -> finish());
    }
}