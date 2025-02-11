package com.example.incidencia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.appincidencias.R;

import java.text.SimpleDateFormat;

import gestionincidencias.entidades.EntIncidencia;

public class AdaptadorIncidencia extends ArrayAdapter<EntIncidencia> {
    private EntIncidencia[] incidencias;

    public AdaptadorIncidencia(Context c, EntIncidencia[] incidencias) {
        super(c, R.layout.elemento_incidencia, incidencias);
        this.incidencias = incidencias;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View vIncidencia = inflater.inflate(R.layout.elemento_incidencia, parent, false);

        TextView txCodigoIncidencia = vIncidencia.findViewById(R.id.codigoIncidencia);
        TextView txDescripcionIncidencia = vIncidencia.findViewById(R.id.descripcionIncidencia);
        TextView txUsuarioIncidencia = vIncidencia.findViewById(R.id.usuarioCreacionIncidencia);
        TextView txFechaIncidencia = vIncidencia.findViewById(R.id.fechaCreacionIncidencia);

        txCodigoIncidencia.setText("Codigo Incidencia: " + String.valueOf(incidencias[position].getCodigoIncidencia()));
        txDescripcionIncidencia.setText("Descripcion: " + incidencias[position].getDescripcion());
        txUsuarioIncidencia.setText("Usuario: " + incidencias[position].getUsuarioCreacion().getNombre());
        txFechaIncidencia.setText("Fecha: " + new SimpleDateFormat("dd/MM/yyyy").format(incidencias[position].getFechaCreacion()));

        return vIncidencia;
    }
}
