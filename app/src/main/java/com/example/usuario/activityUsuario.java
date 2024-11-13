package com.example.usuario;

import static com.example.appincidencias.R.*;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appincidencias.R;
import com.example.menu3botones;

import gestionincidencias.GestionIncidencias;
import gestionincidencias.entidades.EntUsuario;

public class activityUsuario extends menu3botones {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(layout.activity_usuario); //ASEGURARSE DE QUE ESTE EN SU XML
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });




        ListView listaUsuarios = (ListView) findViewById(R.id.listaUsuarios);
        AdaptadorUsuario adaptadorUsuario = new AdaptadorUsuario(this, GestionIncidencias.getArUsuarios().toArray(new EntUsuario[0]));

        listaUsuarios.setAdapter(adaptadorUsuario);

    }
}