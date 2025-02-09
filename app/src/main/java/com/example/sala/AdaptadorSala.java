package com.example.sala;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.appincidencias.R;

import gestionincidencias.entidades.EntSala;

public class AdaptadorSala extends ArrayAdapter<EntSala> {
    private EntSala[] datos;

    public AdaptadorSala(Context c, EntSala[] salas) {
        super(c, R.layout.elemento_sala, salas);
        this.datos = salas;
    }

    //Para hacer un buscador
    //Actualizarsala en el activity
    //En el helper un filtrarsala
    //Y setonquerylistener que genera solos

    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {
        //INFLAMOS LA LISTA PARA MOSTRAR CADA SALA
        LayoutInflater mostrado = LayoutInflater.from(getContext());
        View vSala = mostrado.inflate(R.layout.elemento_sala, parent,false);

        //OBTENEMOS LO QUE QUEREMOS MOSTRAR EN LA LISTA
        TextView txCodigoSala = vSala.findViewById(R.id.codigoSala);
        TextView txNombre = vSala.findViewById(R.id.nombreSala);
        TextView txDescripcion = vSala.findViewById(R.id.descripcionSala);

        //ASIGNAMOS LOS VALORES A MOSTRAR
        txCodigoSala.setText("Codigo Sala: " + String.valueOf(datos[position].getCodigoSala()));
        txNombre.setText("Nombre: " + String.valueOf(datos[position].getNombre()));
        txDescripcion.setText("Descripcion: " + String.valueOf(datos[position].getDescripcion()));

        return vSala;

    }
}
