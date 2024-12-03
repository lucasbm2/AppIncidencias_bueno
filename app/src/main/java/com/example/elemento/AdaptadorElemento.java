package com.example.elemento;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.appincidencias.R;
import gestionincidencias.entidades.EntElemento;

public class AdaptadorElemento extends ArrayAdapter<EntElemento> {

    private EntElemento[] elementos;

    public AdaptadorElemento(Context c, EntElemento[] elementos) {
        super(c, R.layout.elemento_elemento, elementos);
        this.elementos = elementos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.elemento_elemento, parent, false);

        TextView txCodigoElemento = view.findViewById(R.id.codigoElemento);
        TextView txNombre = view.findViewById(R.id.nombreElemento);
        TextView txDescrip = view.findViewById(R.id.descripcionElemento);

        txNombre.setText("Nombre: " + String.valueOf(elementos[position].getNombre()));
        txCodigoElemento.setText("Codigo Elemento: " + String.valueOf(elementos[position].getCodigoElemento()));
        txDescrip.setText("Descripcion: " + String.valueOf(elementos[position].getDescripcion()));

        return view;
    }
}
