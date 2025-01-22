package com.example;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appincidencias.R;
import com.example.database.BBDDIncidencias;
import com.example.elemento.ElementoDBHelper;
import com.example.incidencia.IncidenciaDatabaseHelper;
import com.example.prestamo.PrestamoDatabaseHelper;
import com.example.rol.RolDatabaseHelper;
import com.example.rol.activityRoles;
import com.example.sala.SalaDatabaseHelper;
import com.example.sala.activitySalas;
import com.example.elemento.activityElemento;
import com.example.incidencia.activityIncidencia;
import com.example.prestamo.activityPrestamo;
import com.example.tipo.TipoDatabaseHelper;
import com.example.tipo.activityTipo;
import com.example.ubicacion.UbicacionDatabaseHelper;
import com.example.ubicacion.activityUbicacion;
import com.example.usuario.UsuarioDatabaseHelper;
import com.example.usuario.activityUsuario;

import java.text.ParseException;
import java.util.ArrayList;

import gestionincidencias.GestionIncidencias;
import gestionincidencias.entidades.EntElemento;
import gestionincidencias.entidades.EntIncidencia;
import gestionincidencias.entidades.EntPrestamo;
import gestionincidencias.entidades.EntRol;
import gestionincidencias.entidades.EntSala;
import gestionincidencias.entidades.EntTipo;
import gestionincidencias.entidades.EntUbicacion;
import gestionincidencias.entidades.EntUsuario;

public class MenuPrincipal extends menu3botones implements View.OnClickListener {
    private ActivityResultLauncher<Intent> launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_principal);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());


            String ultima = getUltimaActividad(getSharedPreferences("datos", MODE_PRIVATE));

            if (ultima != null && !ultima.isEmpty()) {
                if (ultima.equals(activitySalas.class.toString())) {
                    Intent intentSala = new Intent(this, activitySalas.class);
                    startActivity(intentSala);
                }
                guardaActividad(getSharedPreferences("datos", MODE_PRIVATE), "");
            }
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });




        //Creamos una variable para cada boton, asociando cada uno con su id
        Button botonUsuarios = findViewById(R.id.botonUsuarios);
        // A este boton le damos un onClickListener para que cuando se pulse se abra la activity correspondiente
        botonUsuarios.setOnClickListener((View.OnClickListener) this);

        Button botonElemento = findViewById(R.id.botonElemento);
        botonElemento.setOnClickListener((View.OnClickListener) this);

        Button botonIncidencia = findViewById(R.id.botonIncidencia);
        botonIncidencia.setOnClickListener((View.OnClickListener) this);

        Button botonTipo = findViewById(R.id.botonTipo);
        botonTipo.setOnClickListener((View.OnClickListener) this);

        Button botonPrestamo = findViewById(R.id.botonPrestamo);
        botonPrestamo.setOnClickListener((View.OnClickListener) this);

        Button botonSala = findViewById(R.id.botonSala);
        botonSala.setOnClickListener((View.OnClickListener) this);

        Button botonUbicacion = findViewById(R.id.botonUbicacion);
        botonUbicacion.setOnClickListener((View.OnClickListener) this);

        Button botonRoles = findViewById(R.id.botonRoles);
        botonRoles.setOnClickListener((View.OnClickListener) this);

        //PARA CREAR BASE DE DATOS
        BBDDIncidencias bbdd = new BBDDIncidencias(this, "BBDDIncidencias", null, 1);
        SQLiteDatabase db = bbdd.getWritableDatabase();
//        //Para que actualice la base de datos al abrirlo
//        bbdd.onUpgrade(db,0,0);
        //SI LA COMENTO GUARDA LOS DATOS Y NO ACTUALIZA CADA VEZ
        try {
            cargaDatosBBDD();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private void cargaDatosBBDD() throws ParseException {
        try (SalaDatabaseHelper sdh = new SalaDatabaseHelper(this, "BBDDIncidencias", null, 1)) {
            ArrayList<EntSala> arSalas = sdh.getSalas();
            if (arSalas == null || arSalas.isEmpty()) {
                arSalas = GestionIncidencias.getArSalas();
                for (EntSala sala : arSalas) {
                    sdh.crearSala(sala);
                }
            }
        }

        try (TipoDatabaseHelper tdh = new TipoDatabaseHelper(this, "BBDDIncidencias", null, 1)) {
            ArrayList<EntTipo> arTipos = tdh.getTipos();
            if (arTipos == null || arTipos.isEmpty()) {
                arTipos = GestionIncidencias.getArTipos();
                for (EntTipo tipo : arTipos) {
                    tdh.crearTipo(tipo);
                }
            }
        }

        try (UbicacionDatabaseHelper udh = new UbicacionDatabaseHelper(this, "BBDDIncidencias", null, 1)) {
            ArrayList<EntUbicacion> arUbicacion = udh.getUbicaciones();
            if (arUbicacion == null || arUbicacion.isEmpty()) {
                arUbicacion = GestionIncidencias.getArUbicaciones();
                for (EntUbicacion ubicacion : arUbicacion) {
                    udh.crearUbicacion(ubicacion);
                }
            }
        }

        try (ElementoDBHelper edh = new ElementoDBHelper(this, "BBDDIncidencias", null, 1)) {
            ArrayList<EntElemento> arElementos = edh.getElementos();
            if (arElementos == null || arElementos.isEmpty()) {
                arElementos = GestionIncidencias.getArElementos();
                for (EntElemento elemento : arElementos) {
                    edh.crearElemento(elemento);
                }
            }
        }

        try (UsuarioDatabaseHelper usdh = new UsuarioDatabaseHelper(this, "BBDDIncidencias", null, 1)) {
            ArrayList<EntUsuario> arUsuarios = usdh.getUsuarios();
            if (arUsuarios == null || arUsuarios.isEmpty()) {
                arUsuarios = GestionIncidencias.getArUsuarios();
                for (EntUsuario usuario : arUsuarios) {
                    usdh.crearUsuario(usuario);
                }
            }
        }

        try (RolDatabaseHelper rdh = new RolDatabaseHelper(this, "BBDDIncidencias", null, 1)) {
            ArrayList<EntRol> arRoles = rdh.getRoles();
            if (arRoles == null || arRoles.isEmpty()) {
                arRoles = GestionIncidencias.getArRoles();
                for (EntRol rol : arRoles) {
                    rdh.crearRol(rol);
                }
            }
        }

        try (IncidenciaDatabaseHelper idh = new IncidenciaDatabaseHelper(this, "BBDDIncidencias", null, 1)) {
            ArrayList<EntIncidencia> arIncidencias = idh.getIncidencias();
            if (arIncidencias == null || arIncidencias.isEmpty()) {
                arIncidencias = GestionIncidencias.getArIncidencias();
                for (EntIncidencia incidencia : arIncidencias) {
                    idh.crearIncidencia(incidencia);
                }
            }
        }

        try (PrestamoDatabaseHelper pdh = new PrestamoDatabaseHelper(this, "BBDDIncidencias", null, 1)) {
            ArrayList<EntPrestamo> arPrestamos = pdh.getPrestamos();
            if (arPrestamos == null || arPrestamos.isEmpty()) {
                arPrestamos = GestionIncidencias.getArPrestamos();
                for (EntPrestamo prestamo : arPrestamos) {
                    pdh.crearPrestamo(prestamo);
                }
            }
        }
    }


    //Con el metodo sobreescrito onClick, el cual nos obliga a implementar la interface
    //Configuramos los botones para indicar hacia que actividad queremos que vaya
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.botonUsuarios) {
            //Creamos un intent al que indicamos la actividad a la que queremos ir
            Intent botonUsuarios = new Intent(MenuPrincipal.this, activityUsuario.class);
            //Lanzamos el intent
            startActivity(botonUsuarios);
        } else if (view.getId() == R.id.botonElemento) {
            Intent botonElemento = new Intent(MenuPrincipal.this, activityElemento.class);
            startActivity(botonElemento);
        } else if (view.getId() == R.id.botonIncidencia) {
            Intent botonIncidencia = new Intent(MenuPrincipal.this, activityIncidencia.class);
            startActivity(botonIncidencia);
        } else if (view.getId() == R.id.botonTipo) {
            Intent botonTipo = new Intent(MenuPrincipal.this, activityTipo.class);
            startActivity(botonTipo);
        } else if (view.getId() == R.id.botonPrestamo) {
            Intent botonPrestamo = new Intent(MenuPrincipal.this, activityPrestamo.class);
            startActivity(botonPrestamo);
        } else if (view.getId() == R.id.botonSala) {
            Intent botonSala = new Intent(MenuPrincipal.this, activitySalas.class);
            startActivity(botonSala);
        } else if (view.getId() == R.id.botonUbicacion) {
            Intent botonUbicacion = new Intent(MenuPrincipal.this, activityUbicacion.class);
            startActivity(botonUbicacion);
        } else if (view.getId() == R.id.botonRoles) {
            Intent botonRoles = new Intent(MenuPrincipal.this, activityRoles.class);
            startActivity(botonRoles);
        }
    }



}