package com.example.rol;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appincidencias.R;
import com.example.menu3botones;
import com.example.sala.ficha_sala;

import java.util.ArrayList;

import gestionincidencias.entidades.EntRol;

public class activityRoles extends menu3botones {

    private ListView listaRoles;
    private AdaptadorRol adaptadorRol;
    private RolDatabaseHelper rdh;
    private SearchView buscaRoles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_roles);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        guardaActividad(getSharedPreferences("datos", MODE_PRIVATE), activityRoles.class.toString());

        // BUSCAMOS LA LISTVIEW DE ROLES
        listaRoles = findViewById(R.id.listaRoles);
        // CREAMOS UN ADAPTADOR PARA LA LISTA DE ROLES
        rdh = new RolDatabaseHelper(this, "BBDDIncidencias", null, 1);
        ArrayList<EntRol> arRoles = rdh.getRoles();
        adaptadorRol = new AdaptadorRol(this, arRoles.toArray(new EntRol[0]));

        // ESTABLECEMOS ADAPTADOR A LA LISTA
        listaRoles.setAdapter(adaptadorRol);

        // BUSCAMOS LA BUSQUEDA
        buscaRoles = findViewById(R.id.searchViewRoles);
        buscaRoles.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String buscar) {
                // No necesitamos hacer nada aquí, ya que filtramos en onQueryTextChange
                return false;
            }

            @Override
            public boolean onQueryTextChange(String buscarNuevo) {
                actualizarLista(buscarNuevo);
                return true;
            }
        });

        // OYENTE PARA QUE CUANDO HAGAMOS CLICK SE ABRA LA FICHA SELECCIONADA
        listaRoles.setOnItemClickListener((adapterView, view, position, id) -> {
            EntRol rolSeleccionado = (EntRol) adapterView.getItemAtPosition(position);

            Intent intentFichaRol = new Intent(view.getContext(), ficha_rol.class);

            intentFichaRol.putExtra("codigoRol", rolSeleccionado.getCodigo());
            intentFichaRol.putExtra("NombreRol", rolSeleccionado.getNombre());
            intentFichaRol.putExtra("descripcionRol", rolSeleccionado.getDescripcion());
            intentFichaRol.putExtra("nivelAccesoRol", rolSeleccionado.getNivel_acceso());

            startActivity(intentFichaRol);
        });

        // BOTON Y FUNCION PARA AÑADIR UNA NUEVA SALA
        Button añadirRol = findViewById(R.id.añadirRol);
        añadirRol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAñadirRol = new Intent(view.getContext(), ficha_rol.class);

                intentAñadirRol.putExtra("codigoRol", 0);
                intentAñadirRol.putExtra("NombreRol", "");
                intentAñadirRol.putExtra("descripcionRol", "");
                intentAñadirRol.putExtra("nivelAccesoRol", "");

                startActivity(intentAñadirRol);
            }
        });
    }

    private void actualizarLista(String texto) {
        Log.d("activityRoles", "Texto de búsqueda: " + texto);
        ArrayList<EntRol> rolVistos = rdh.buscadorRol(texto);
        Log.d("activityRoles", "Roles encontrados: " + rolVistos.size());
        adaptadorRol = new AdaptadorRol(this, rolVistos.toArray(new EntRol[0]));
        listaRoles.setAdapter(adaptadorRol);
    }
}