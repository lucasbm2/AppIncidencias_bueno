package com.example.usuario;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.database.BBDDIncidencias;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import gestionincidencias.entidades.EntIncidencia;
import gestionincidencias.entidades.EntUsuario;

public class UsuarioDatabaseHelper extends BBDDIncidencias {
    public UsuarioDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public long crearUsuario(EntUsuario usuario) {
        SQLiteDatabase db = this.getWritableDatabase();
        long usuarioId = -1;

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            if (usuario.getCodigoUsuario() > 0) {
                values.put(KEY_COL_CODIGO_USUARIO, usuario.getCodigoUsuario());
            }
//            Log.d("IncidenciaDataBaseHelper", "Iniciando inserción de usuario...");
//            Log.d("IncidenciaDataBaseHelper", "Valores para insertar: " + values.toString());
            values.put(KEY_COL_NOMBRE_USUARIO, usuario.getNombre());
            values.put(KEY_COL_CORREO_USUARIO, usuario.getCorreo());
            values.put(KEY_COL_TELEFONO_USUARIO, usuario.getTelefono());
            values.put(KEY_COL_PASSWORD_USUARIO, usuario.getPassword());
            values.put(KEY_COL_CODIGO_ROL_USUARIO, usuario.getRol());

            //si no existe y no se puede actualizar entonces lo creamos
            usuarioId = db.insertOrThrow(TABLE_USUARIO, null, values);
//            Log.d("IncidenciaDatabaseHelper", "Usuario insertado con éxito. ID generado: " + usuarioId);
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.e("IncidenciaDatabaseHelper", "Error al añadir usuario: " + e.getMessage(), e);
        } finally {
            db.endTransaction();
        }
        return usuarioId;
    }

    public long actualizarUsuario(EntUsuario usuario) {
        SQLiteDatabase db = this.getWritableDatabase();
        long usuarioId = -1;

        db.beginTransaction();
        if (usuario.getCodigoUsuario() > 0) {
            try {
                ContentValues values = new ContentValues();
//                Log.d("UsuarioDatabaseHelper", "Valores a insertar: " + values.toString());
                values.put(KEY_COL_CODIGO_USUARIO, usuario.getCodigoUsuario());
                values.put(KEY_COL_NOMBRE_USUARIO, usuario.getNombre());
                values.put(KEY_COL_CORREO_USUARIO, usuario.getCorreo());
                values.put(KEY_COL_TELEFONO_USUARIO, usuario.getTelefono());
                values.put(KEY_COL_PASSWORD_USUARIO, usuario.getPassword());
                values.put(KEY_COL_CODIGO_ROL_USUARIO, usuario.getRol());


                //Primero intento actualizar elemento concreto
                int rows = db.update(TABLE_USUARIO, values, KEY_COL_CODIGO_USUARIO + " = ?",
                        new String[]{String.valueOf(usuario.getCodigoUsuario())});
                if (rows > 0) {
                    usuarioId = usuario.getCodigoUsuario();
                    db.setTransactionSuccessful();
                }
            } catch (Exception e) {
                Log.e("UsuarioDatabaseHelper", "Error al modificar usuario: " + e.getMessage(), e);
            } finally {
                db.endTransaction();
            }
        }
        return usuarioId;
    }

    public ArrayList<EntUsuario> getUsuarios() throws ParseException {
        ArrayList<EntUsuario> salida = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Consulta para obtener todas las ubicaciones
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_USUARIO, null);


        if (c.moveToFirst()) {
            do {
                int codigoUsuario = c.getInt(0);
                String nombre = c.getString(1);
                String correo = c.getString(2);
                String telefono = c.getString(3);
                String password = c.getString(4);
                int rol = c.getInt(5);

                EntUsuario usuario = new EntUsuario(codigoUsuario, nombre, correo, telefono, password, rol);

                salida.add(usuario);
            } while (c.moveToNext());
        }
        c.close();
        return salida;
    }

    public int borrarUsuario(int codigoUsuario) {
        SQLiteDatabase db = this.getWritableDatabase();
        int borrados = 0;

        db.beginTransaction();
        try {
            borrados = db.delete(TABLE_USUARIO, KEY_COL_CODIGO_USUARIO + " = ?",
                    new String[]{String.valueOf(codigoUsuario)});
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.d(UsuarioDatabaseHelper.class.getName(), "Error al intentar borrar usuario");
        } finally {
            db.endTransaction();
        }
        return borrados;
    }
}
