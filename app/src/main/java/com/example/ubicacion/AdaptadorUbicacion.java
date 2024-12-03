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

public class    AdaptadorUbicacion extends ArrayAdapter<EntUbicacion> {

    private EntUbicacion[] ubicaciones;

    public AdaptadorUbicacion(Context c, EntUbicacion[] ubicaciones) {
        super(c, R.layout.elemento_ubicacion, ubicaciones);
        this.ubicaciones = ubicaciones;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //INFLAMOS LA LISTA PARA MOSTRAR EL ELEMENTO DE LA UBICACIÓN
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.elemento_ubicacion, parent, false);

        //OBTENEMOS LO QUE QUEREMOS MOSTRAR EN LA LISTA
        TextView txCodigoUbicacion = view.findViewById(R.id.codigoUbicacion);
        TextView txNombreSala = view.findViewById(R.id.nombreSalaUbicacion);

        //ASIGNAMOS LOS VALORES A MOSTRAR
        txCodigoUbicacion.setText("Codigo Ubicacion: " + String.valueOf(ubicaciones[position].getCodigoUbicacion()));
        txNombreSala.setText(ubicaciones[position].getSala().getNombre());

        return view;
    }
}
