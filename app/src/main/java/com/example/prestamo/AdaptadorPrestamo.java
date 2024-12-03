    package com.example.prestamo;

    import android.content.Context;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ArrayAdapter;
    import android.widget.TextView;

    import com.example.appincidencias.R;

    import org.w3c.dom.Text;

    import java.text.SimpleDateFormat;

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
            TextView txIdUsuario = vPrestamo.findViewById(R.id.nombreUsuarioPrestamo);
            TextView txFechaInicio = vPrestamo.findViewById(R.id.fechaInicioPrestamo);
            TextView txNombreUsuario = vPrestamo.findViewById(R.id.nombreUsuarioPrestamo);

            txCodigoPrestamo.setText("Codigo Prestamo: " + String.valueOf(prestamos[position].getCodigoPrestamo()));
            txIdUsuario.setText("ID: " + String.valueOf(prestamos[position].getIdUsuario()));
            txNombreUsuario.setText("Nombre: " + prestamos[position].getUsuario().getNombre());
            txFechaInicio.setText("Fecha Inicio: " + new SimpleDateFormat("dd/MM/yyyy").format(prestamos[position].getFechaInicio()));

            return vPrestamo;

        }
    }
