package com.example.ubicacion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.appincidencias.R;

import gestionincidencias.entidades.EntUbicacion;

public class AdaptadorUbicacion extends ArrayAdapter<EntUbicacion> {
    private EntUbicacion[] ubicaciones;

    public AdaptadorUbicacion(Context c, EntUbicacion[] ubicaciones) {
        super(c, R.layout.elemento_ubicacion, ubicaciones);
        this.ubicaciones = ubicaciones;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater mostrado = LayoutInflater.from(getContext());
        View vUbicacion = mostrado.inflate(R.layout.elemento_ubicacion, parent, false);

        TextView txCodigoUbicacion = vUbicacion.findViewById(R.id.codigoUbicacion);
        TextView txIdSala = vUbicacion.findViewById(R.id.idSala);
        TextView txIdElemento = vUbicacion.findViewById(R.id.idElementoUbicacion);
        TextView txDescripcion = vUbicacion.findViewById(R.id.descripcion);
        TextView txFechaInicio = vUbicacion.findViewById(R.id.fechaInicio);
        TextView txFechaFin = vUbicacion.findViewById(R.id.fechaFin);

        txCodigoUbicacion.setText(String.valueOf(ubicaciones[position].getCodigoUbicacion()));
        txIdSala.setText(String.valueOf(ubicaciones[position].getIdSala()));
        txIdElemento.setText(String.valueOf(ubicaciones[position].getIdElemento()));
        txDescripcion.setText(String.valueOf(ubicaciones[position].getDescripcion()));
        txFechaInicio.setText(String.valueOf(ubicaciones[position].getFechaInicio()));
        txFechaFin.setText(String.valueOf(ubicaciones[position].getFechaFin()));

        return vUbicacion;
    }
}
