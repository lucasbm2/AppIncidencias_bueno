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

    private EntElemento[] datos ;

    public AdaptadorElemento(Context c, EntElemento[] elementos) {
        super(c, R.layout.elemento_elemento, elementos);
        this.datos = elementos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater mostrado = LayoutInflater.from(getContext());
        View vElemento = mostrado.inflate(R.layout.elemento_elemento, parent, false);

        TextView txCodigoElemento = vElemento.findViewById(R.id.codigoElemento);
        TextView txNombre = vElemento.findViewById(R.id.nombreElemento);
        TextView txDescrip = vElemento.findViewById(R.id.descripcionElemento);
        TextView txIdTipo = vElemento.findViewById(R.id.idTipo);
        TextView txTipoElemento = vElemento.findViewById(R.id.tipoElemento);

        txNombre.setText(String.valueOf(datos[position].getNombre()));
        txCodigoElemento.setText(String.valueOf(datos[position].getCodigoElemento()));
        txDescrip.setText(String.valueOf(datos[position].getDescripcion()));
        txIdTipo.setText(String.valueOf(datos[position].getIdTipo()));
        txTipoElemento.setText(String.valueOf(datos[position].getTipoElemento()));

        return vElemento;
    }
}
