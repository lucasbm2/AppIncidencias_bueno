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
        LayoutInflater mostrado = LayoutInflater.from(getContext());
        View vIncidencia = mostrado.inflate(R.layout.elemento_incidencia, parent, false);

        TextView txCodigoIncidencia = vIncidencia.findViewById(R.id.codigoIncidencia);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String fechaFormateada = sdf.format(incidencias[position].getFechaCreacion());

        txCodigoIncidencia.setText(String.valueOf(incidencias[position].getCodigoIncidencia()));

        return vIncidencia;
    }
}
