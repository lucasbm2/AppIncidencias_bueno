package com.example.usuario;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.database.BBDDIncidencias;

import java.util.ArrayList;

import gestionincidencias.entidades.EntUsuario;

public class UsuarioDBHelper extends BBDDIncidencias {
    public UsuarioDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public long crearUsuario(EntUsuario usuario) {
        SQLiteDatabase db = getWritableDatabase();
        long usuarioID = -1;

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            if (usuario.getCodigoUsuario() > 0) {
                values.put(KEY_COL_CODIGO_USUARIO, usuario.getCodigoUsuario());
            }
            values.put(KEY_COL_NOMBRE_USUARIO, usuario.getNombre());
            values.put(KEY_COL_CORREO_USUARIO, usuario.getCorreo());
            values.put(KEY_COL_TELEFONO_USUARIO, usuario.getTelefono());
            values.put(KEY_COL_PASSWORD_USUARIO, usuario.getPassword());
            values.put(KEY_COL_CODIGO_ROL_USUARIO, usuario.getRol());

            usuarioID = db.insertOrThrow(TABLE_USUARIO, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(UsuarioDBHelper.class.getName(), e.getMessage());
        } finally {
            db.endTransaction();
        }
        return usuarioID;
    }

    public long actualizarUsuario(EntUsuario usuario) {
        SQLiteDatabase db = getWritableDatabase();
        long usuarioID = -1;

        if (usuario.getCodigoUsuario() > 0) {
            db.beginTransaction();

            try {
                ContentValues values = new ContentValues();

                if (usuario.getCodigoUsuario() > 0) {
                    values.put(KEY_COL_CODIGO_USUARIO, usuario.getCodigoUsuario());
                }
                values.put(KEY_COL_NOMBRE_USUARIO, usuario.getNombre());
                values.put(KEY_COL_CORREO_USUARIO, usuario.getCorreo());
                values.put(KEY_COL_TELEFONO_USUARIO, usuario.getTelefono());
                values.put(KEY_COL_PASSWORD_USUARIO, usuario.getPassword());
                values.put(KEY_COL_CODIGO_ROL_USUARIO, usuario.getRol());

                int rows = db.update(TABLE_USUARIO, values, KEY_COL_CODIGO_USUARIO + " = ?",
                        new String[]{String.valueOf(usuario.getCodigoUsuario())});

                if (rows > 0){
                    db.setTransactionSuccessful();
                    usuarioID = usuario.getCodigoUsuario();
                }

            } catch (Exception e) {
                Log.d(UsuarioDBHelper.class.getName(), e.getMessage());
            } finally {
                db.endTransaction();
            }
        }
        return usuarioID;
    }

    public ArrayList<EntUsuario> getUsuarios() {
        ArrayList<EntUsuario> usuarios = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USUARIO, null);

        if (cursor.moveToFirst()){
            do {
                int codigo = cursor.getInt(0);
                String nombre = cursor.getString(1);
                String correo = cursor.getString(2);
                String telefono = cursor.getString(3);
                String password = cursor.getString(4);
                int codigoRol = cursor.getInt(5);

                EntUsuario usuario = new EntUsuario(codigo, nombre, correo, telefono, password, codigoRol);
                usuarios.add(usuario);
            }while (cursor.moveToNext());
        }
        return usuarios;
    }

    public int borrarUsuario(int codigo) {
        SQLiteDatabase db = getWritableDatabase();
        int borrados = 0;

        db.beginTransaction();

        try {
            borrados = db.delete(TABLE_USUARIO, KEY_COL_CODIGO_USUARIO + " = ?", new String[]{String.valueOf(codigo)});
            db.setTransactionSuccessful();
        }catch (Exception e) {
            Log.d(UsuarioDBHelper.class.getName(), e.getMessage());
        } finally {
            db.endTransaction();
        }
        return borrados;
    }


}
