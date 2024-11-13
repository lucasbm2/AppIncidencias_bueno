    package com.example.incidencia;

    import android.annotation.SuppressLint;
    import android.os.Bundle;
    import android.widget.ListView;

    import androidx.activity.EdgeToEdge;
    import androidx.core.graphics.Insets;
    import androidx.core.view.ViewCompat;
    import androidx.core.view.WindowInsetsCompat;

    import com.example.appincidencias.R;
    import com.example.menu3botones;

    import gestionincidencias.GestionIncidencias;
    import gestionincidencias.entidades.EntIncidencia;

    public class activityIncidencia extends menu3botones {

        @SuppressLint("MissingInflatedId")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_incidencia); //ASEGURARSE DE QUE ESTE EN SU XML
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });




            ListView listaIncidencias = (ListView) findViewById(R.id.listaIncidencias);
            AdaptadorIncidencia adaptadorIncidencia   = new AdaptadorIncidencia(this, GestionIncidencias.getArIncidencias().toArray(new EntIncidencia[0]));

            listaIncidencias.setAdapter(adaptadorIncidencia);

        }
    }
