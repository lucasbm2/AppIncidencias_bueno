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
        TextView txDescripcion = vIncidencia.findViewById(R.id.descripcion);
        TextView txIdElemento = vIncidencia.findViewById(R.id.idElemento);
        TextView txFechaCreacion = vIncidencia.findViewById(R.id.fechaCreacion);
        TextView txIdUsuarioCreacion = vIncidencia.findViewById(R.id.idUsuarioCreacion);
        TextView txUsuarioCreacion = vIncidencia.findViewById(R.id.usuarioCreacion);
        TextView txElemento = vIncidencia.findViewById(R.id.elemento);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String fechaFormateada = sdf.format(incidencias[position].getFechaCreacion());

        txCodigoIncidencia.setText(String.valueOf(incidencias[position].getCodigoIncidencia()));
        txDescripcion.setText(incidencias[position].getDescripcion());
        txIdElemento.setText(String.valueOf(incidencias[position].getIdElemento()));
        txFechaCreacion.setText(fechaFormateada); // Mostrar la fecha formateada
        txIdUsuarioCreacion.setText(String.valueOf(incidencias[position].getIdUsuarioCreacion()));
        txUsuarioCreacion.setText(incidencias[position].getUsuarioCreacion().getNombre());
        txElemento.setText(incidencias[position].getElemento().getNombre());

        return vIncidencia;
    }
}
