package com.example.incidencia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.appincidencias.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        EntIncidencia incidencia = incidencias[position];

        TextView txCodigoIncidencia = vIncidencia.findViewById(R.id.codigoIncidencia);
        TextView txDescripcionIncidencia = vIncidencia.findViewById(R.id.descripcionIncidencia);
        TextView txUsuarioIncidencia = vIncidencia.findViewById(R.id.usuarioCreacionIncidencia);
        TextView txFechaIncidencia = vIncidencia.findViewById(R.id.fechaCreacionIncidencia);

        // Asignar valores correctamente
        txCodigoIncidencia.setText("Código Incidencia: " + incidencia.getCodigoIncidencia());
        txDescripcionIncidencia.setText("Descripción: " + incidencia.getDescripcion());
        txUsuarioIncidencia.setText("Usuario: " + (incidencia.getUsuarioCreacion() != null ? incidencia.getUsuarioCreacion().getNombre() : "Desconocido"));

        // Validación de fecha
        Date fechaCreacion = incidencia.getFechaCreacion();
        String fechaFormateada = (fechaCreacion != null) ? sdf.format(fechaCreacion) : "Fecha no disponible";

        txFechaIncidencia.setText("Fecha: " + fechaFormateada);

        return vIncidencia;
    }
}
