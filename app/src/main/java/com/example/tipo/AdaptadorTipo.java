    package com.example.tipo;

    import android.content.Context;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ArrayAdapter;
    import android.widget.TextView;


    import com.example.appincidencias.R;

    import gestionincidencias.entidades.EntTipo;

    public class AdaptadorTipo extends ArrayAdapter<EntTipo> {
        private EntTipo[] tipos;

        public AdaptadorTipo(Context c, EntTipo[] tipos) {
            super(c, R.layout.elemento_tipo, tipos);
            this.tipos = tipos;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater mostrado = LayoutInflater.from(getContext());
            View vTipo = mostrado.inflate(R.layout.elemento_tipo, parent,false);

            TextView txCodigo = vTipo.findViewById(R.id.codigoTipo);
            TextView txNombre = vTipo.findViewById(R.id.nombreTipo);
            TextView txDescripcion = vTipo.findViewById(R.id.descripcionTipo);

            txCodigo.setText(String.valueOf(tipos[position].getCodigoTipo()));
            txNombre.setText(String.valueOf(tipos[position].getNombre()));
            txDescripcion.setText(String.valueOf(tipos[position].getDescripcion()));

            return vTipo;

        }

    }
