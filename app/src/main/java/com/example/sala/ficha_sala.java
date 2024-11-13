package com.example.sala;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appincidencias.R;

public class ficha_sala extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha_sala);

        //Creamos un intent para poder obtener los datos de la otra actividad (activitySalas)
        Intent infoFichaSala = this.getIntent();

        //Creamos un Bundle para almacenar los datos de la otra actividad (activitySalas)
        Bundle bnd = infoFichaSala.getExtras();

        //Creamos variables para guardar los datos almacenados en el Bundle, usamos el getString y enre parentesis el nombre
        //que hemos puesto antes en el putExtra, Ejemplo: intenFichaSala.putExtra("sala", salaSeleccionada.getCodigoSala());
        //Esto esta sacado del OnItemClick de la activitySalas. En este caso "sala" seria el nombre
        String codSala = String.valueOf(bnd.getInt("sala"));
        String nombreSala = bnd.getString("nombre");
        String descripcionSala = bnd.getString("descripcion");

        //Creamos variables TextView para encontrar el elemento donde queremos mostrar los datos obtenidos.
        //Estos estan en activity_ficha_sala.xml en mi caso.
        TextView txtCodSala = findViewById(R.id.codSala);
        txtCodSala.setText(String.valueOf(codSala));
        TextView txtNombreSala = findViewById(R.id.nombreSala);
        txtNombreSala.setText(nombreSala);
        TextView txtDescripcionSala = findViewById(R.id.descripcionSala);
        txtDescripcionSala.setText(descripcionSala);
    }
}
