    package com.example.usuario;

    import android.content.Context;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ArrayAdapter;
    import android.widget.TextView;


import com.example.appincidencias.R;

    import gestionincidencias.entidades.EntUsuario;

public class AdaptadorUsuario extends ArrayAdapter<EntUsuario> { //CAMBIAR AL TIPO INDICADO
    private EntUsuario[] datos;

    public AdaptadorUsuario(Context c, EntUsuario[] usuarios) { //CAMBIAR AL TIPO INDICADO
        super(c, R.layout.elemento_usuario, usuarios);
        this.datos = usuarios;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater mostrado = LayoutInflater.from(getContext());
        View view = mostrado.inflate(R.layout.elemento_usuario, parent, false);//CAMBIAR AL TIPO INDICADO

        TextView txCodigoUsuario = view.findViewById(R.id.codigoUsuario);//ELEMENTOS DEL USUARIO
        TextView txNombre = view.findViewById(R.id.nombreUsuario);//ELEMENTOS DEL USUARIO
        TextView txRol = view.findViewById(R.id.rolUsuario);//ELEMENTOS DEL USUARIO


        txCodigoUsuario.setText("Codigo Usuario: " + String.valueOf(datos[position].getCodigoUsuario()));
        txNombre.setText("Nombre: " + String.valueOf(datos[position].getNombre()));
        txRol.setText("Rol: " + String.valueOf(datos[position].getRol()));

        return view;

    }

}
