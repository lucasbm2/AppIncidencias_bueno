package com.example.rol;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.appincidencias.R;

import gestionincidencias.entidades.EntRol;
import gestionincidencias.entidades.EntSala;

public class AdaptadorRol extends ArrayAdapter<EntRol> {
    private EntRol[] datos;

    public AdaptadorRol(Context c, EntRol[] roles) {
        super(c, R.layout.elemento_roles, roles);
        this.datos = roles;
    }

    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {
        //INFLAMOS LA LISTA PARA MOSTRAR CADA ROL
        LayoutInflater mostrado = LayoutInflater.from(getContext());
        View vRol = mostrado.inflate(R.layout.elemento_roles, parent,false);

        //OBTENEMOS LO QUE QUEREMOS MOSTRAR EN LA LISTA
        TextView txCodigoRol = vRol.findViewById(R.id.codigoRol);
        TextView txNombre = vRol.findViewById(R.id.nombreRol);
        TextView txDescripcion = vRol.findViewById(R.id.descripcionRol);
        TextView txNivelAcceso = vRol.findViewById(R.id.nivelAccesoRol);

        //ASIGNAMOS LOS VALORES A MOSTRAR
        txCodigoRol.setText("Codigo Rol: " + String.valueOf(datos[position].getCodigo()));
        txNombre.setText("Nombre: " + String.valueOf(datos[position].getNombre()));
        txDescripcion.setText("Descripcion: " + String.valueOf(datos[position].getDescripcion()));
        txNivelAcceso.setText("Nivel de acceso: " + String.valueOf(datos[position].getNivel_acceso()));

        return vRol;

    }
}
