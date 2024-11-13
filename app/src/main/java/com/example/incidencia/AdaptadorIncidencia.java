    package com.example.incidencia;

    import android.content.Context;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ArrayAdapter;
    import android.widget.TextView;

    import com.example.appincidencias.R;

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
            View vIncidencia = mostrado.inflate(R.layout.elemento_incidencia, parent, false);//CAMBIAR AL TIPO INDICADO

            TextView txCodigoIncidencia = vIncidencia.findViewById(R.id.codigoIncidencia);
            TextView txDescripcion = vIncidencia.findViewById(R.id.descripcion);
            TextView txIdElemento = vIncidencia.findViewById(R.id.idElemento);
            TextView txFechaCreacion = vIncidencia.findViewById(R.id.fechaCreacion);
            TextView txIdUsuarioCreacion = vIncidencia.findViewById(R.id.usuarioCreacion);
            TextView txElemento = vIncidencia.findViewById(R.id.elemento);

            txCodigoIncidencia.setText(String.valueOf(incidencias[position].getCodigoIncidencia()));
            txDescripcion.setText((String.valueOf(incidencias[position].getDescripcion())));
            txIdElemento.setText(String.valueOf(incidencias[position].getIdElemento()));
            txFechaCreacion.setText(String.valueOf(incidencias[position].getFechaCreacion()));
            txIdUsuarioCreacion.setText(String.valueOf(incidencias[position].getUsuarioCreacion()));
            txElemento.setText(String.valueOf(incidencias[position].getElemento()));


            return vIncidencia;

        }
    }
