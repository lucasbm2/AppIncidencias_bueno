package com.example;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;


import androidx.appcompat.app.AppCompatActivity;

import com.example.appincidencias.R;
import com.example.rol.activityRoles;
import com.example.sala.activitySalas;
import com.example.tipo.activityTipo;
import com.example.elemento.activityElemento;
import com.example.incidencia.activityIncidencia;
import com.example.prestamo.activityPrestamo;
import com.example.usuario.activityUsuario;
import com.example.ubicacion.activityUbicacion;

public class menu3botones extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.itemMenuSalas) {
            Intent intentSalas = new Intent(this, activitySalas.class);
            startActivity(intentSalas);
            return true;
        } else if (item.getItemId() == R.id.itemMenuTipos) {
            Intent intentTipos = new Intent(this, activityTipo.class);
            startActivity(intentTipos);
            return true;
        } else if (item.getItemId() == R.id.itemMenuPrincipal ) {
            Intent intentPrincipal = new Intent(this, MenuPrincipal.class);
            startActivity(intentPrincipal);
            return true;
        } else if (item.getItemId() == R.id.itemMenuIncidencias) {
            Intent intentIncidencias = new Intent(this, activityIncidencia.class);
            startActivity(intentIncidencias);
            return true;
        } else if (item.getItemId() == R.id.itemMenuPrestamos) {
            Intent intentPrestamos = new Intent(this, activityPrestamo.class);
            startActivity(intentPrestamos);
            return true;
        } else if (item.getItemId() == R.id.itemMenuElementos) {
            Intent intentElementos = new Intent(this, activityElemento.class);
            startActivity(intentElementos);
            return true;
        } else if (item.getItemId() == R.id.itemMenuUsuarios) {
            Intent intentUsuarios = new Intent(this, activityUsuario.class);
            startActivity(intentUsuarios);
            return true;
        } else if (item.getItemId() == R.id.itemMenuUbicaciones) {
            Intent intentUbicaciones = new Intent(this, activityUbicacion.class);
            startActivity(intentUbicaciones);
            return true;
        }
        else if (item.getItemId() == R.id.itemMenuRoles) {
            Intent intentRoles = new Intent(this, activityRoles.class);
            startActivity(intentRoles);
            return true;
        }
        else if (item.getItemId() == R.id.itemMenuAtras) {
            finish();
            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }

    //PARA GUARDAR LA ULTIMA ACTIVIDAD QUE HE USADO EN UNA VARIABLE
    public void guardaActividad(SharedPreferences prefs, String valor) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("ultimaActividad", valor);
        editor.commit();
    }

    public String getUltimaActividad(SharedPreferences prefs) {
        return prefs.getString("ultimaActividad", "");

    }
}