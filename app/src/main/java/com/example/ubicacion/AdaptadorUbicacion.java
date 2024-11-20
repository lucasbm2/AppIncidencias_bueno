package com.example.ubicacion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.appincidencias.R;

import java.text.SimpleDateFormat;

import gestionincidencias.entidades.EntUbicacion;

public class AdaptadorUbicacion extends ArrayAdapter<EntUbicacion> {

    private EntUbicacion[] ubicaciones;

    public AdaptadorUbicacion(Context c, EntUbicacion[] ubicaciones) {
        super(c, R.layout.elemento_ubicacion, ubicaciones);
        this.ubicaciones = ubicaciones;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.elemento_ubicacion, parent, false);

        TextView txCodigoUbicacion = view.findViewById(R.id.codigoUbicacion);
        TextView txDescripcion = view.findViewById(R.id.descripcion);

        txCodigoUbicacion.setText(String.valueOf(ubicaciones[position].getCodigoUbicacion()));
        txDescripcion.setText(ubicaciones[position].getDescripcion());

        return view;
    }
}
