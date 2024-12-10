    package com.example.rol;

    import android.content.Intent;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.Button;
    import android.widget.ListView;

    import androidx.activity.EdgeToEdge;
    import androidx.core.graphics.Insets;
    import androidx.core.view.ViewCompat;
    import androidx.core.view.WindowInsetsCompat;

    import com.example.appincidencias.R;
    import com.example.menu3botones;
    import com.example.sala.ficha_sala;

    import gestionincidencias.GestionIncidencias;
    import gestionincidencias.entidades.EntRol;

    public class activityRoles extends menu3botones {

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

            //BUSCAMOS LA LISTVIEW DE ROLES
            ListView listaRoles = (ListView) findViewById(R.id.listaRoles);
            //CREAMOS UN ADAPTADOR PARA LA LISTA DE ROLES
            AdaptadorRol adaptadorRol = new AdaptadorRol(this, GestionIncidencias.getArRoles().toArray(new EntRol[0]));

            //ESTABLECEMOS ADAPTADOR A LA LISTA
            listaRoles.setAdapter(adaptadorRol);

            //OYENTE PARA QUE CUANDO HAGAMOS CLICK SE ABRA LA FICHA SELECCIONADA
            listaRoles.setOnItemClickListener((adapterView, view, position, id) -> {
                EntRol rolSeleccionado = (EntRol) adapterView.getItemAtPosition(position);

                Intent intentFichaRol = new Intent(view.getContext(), ficha_rol.class);

                intentFichaRol.putExtra("codigoRol", rolSeleccionado.getCodigo());
                intentFichaRol.putExtra("NombreRol", rolSeleccionado.getNombre());
                intentFichaRol.putExtra("descripcionRol", rolSeleccionado.getDescripcion());
                intentFichaRol.putExtra("nivelAccesoRol", rolSeleccionado.getNivel_acceso());

                startActivity(intentFichaRol);
            });

            //BOTONM Y FUNCION PARA AÑADIR UNA NUEVA SALA
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
    }
