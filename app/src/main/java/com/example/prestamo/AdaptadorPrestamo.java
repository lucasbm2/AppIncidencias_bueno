    package com.example.prestamo;

    import android.content.Context;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ArrayAdapter;
    import android.widget.TextView;

    import com.example.appincidencias.R;

    import gestionincidencias.entidades.EntIncidencia;
    import gestionincidencias.entidades.EntPrestamo;

    public class AdaptadorPrestamo extends ArrayAdapter<EntPrestamo> {
        private EntPrestamo[] prestamos;

        public AdaptadorPrestamo(Context c, EntPrestamo[] prestamos) {
            super(c, R.layout.elemento_prestamo, prestamos);
            this.prestamos = prestamos;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater mostrado = LayoutInflater.from(getContext());
            View vPrestamo = mostrado.inflate(R.layout.elemento_prestamo, parent, false);//CAMBIAR AL TIPO INDICADO

            TextView txCodigoPrestamo = vPrestamo.findViewById(R.id.codigoPrestamo);
            TextView txIdUsuario = vPrestamo.findViewById(R.id.codigoUsuario);
            TextView txIdElemento = vPrestamo.findViewById(R.id.codigoElemento);
            TextView txFechaInicio = vPrestamo.findViewById(R.id.fechaInicioPrestamo);
            TextView txFechaFin = vPrestamo.findViewById(R.id.fechaFinPrestamo);

            txCodigoPrestamo.setText(String.valueOf(prestamos[position].getCodigoPrestamo()));
            txIdUsuario.setText(String.valueOf(prestamos[position].getIdUsuario()));
            txIdElemento.setText(String.valueOf(prestamos[position].getIdElemento()));
            txIdUsuario.setText(String.valueOf(prestamos[position].getIdUsuario()));
            txFechaInicio.setText(String.valueOf(prestamos[position].getFechaInicio()));
            txFechaFin.setText(String.valueOf(prestamos[position].getFechaFin()));

            return vPrestamo;

        }
    }
