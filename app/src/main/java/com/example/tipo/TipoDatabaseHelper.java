package com.example.tipo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.database.BBDDIncidencias;

import java.util.ArrayList;

import gestionincidencias.entidades.EntSala;
import gestionincidencias.entidades.EntTipo;

public class TipoDatabaseHelper extends BBDDIncidencias {
    public TipoDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public long crearTipo(EntTipo tipo) {
        SQLiteDatabase db = this.getWritableDatabase();
        long tipoId = -1;


        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            if (tipo.getCodigoTipo() >0) {
                values.put(KEY_COL_CODIGO_TIPO, tipo.getCodigoTipo());
            }
            values.put(KEY_COL_NOMBRE_TIPO, tipo.getNombre());
            values.put(KEY_COL_DESCRIPCION_TIPO, tipo.getDescripcion());

            //si no existe y no se puede actualizar entonces lo creamos
            tipoId = db.insertOrThrow(TABLE_TIPO, null, values);
            db.setTransactionSuccessful();
            
        } catch (Exception e) {
            Log.d(TipoDatabaseHelper.class.getName(), "Error al anadir tipo");
        } finally {
            db.endTransaction();
        }
        return tipoId;
    }

    public long actualizarTipo (EntTipo tipo) {
        SQLiteDatabase db = this.getWritableDatabase();
        long tipoId = -1;

        db.beginTransaction();
        if (tipo.getCodigoTipo() > 0) {
            try {
                ContentValues values = new ContentValues();
                values.put(KEY_COL_CODIGO_TIPO, tipo.getCodigoTipo());
                values.put(KEY_COL_NOMBRE_TIPO, tipo.getNombre());
                values.put(KEY_COL_DESCRIPCION_TIPO, tipo.getDescripcion());

                //Primero intento actualizar elemento concreto
                int rows = db.update(TABLE_TIPO, values, KEY_COL_CODIGO_TIPO + " = ?",
                        new String[]{String.valueOf(tipo.getCodigoTipo())});
                if (rows > 0) {
                    tipoId = tipo.getCodigoTipo();
                    db.setTransactionSuccessful();
                }
            } catch (Exception e) {
                Log.d(TipoDatabaseHelper.class.getName(), "Error al actualizar tipo");
            } finally {
                db.endTransaction();
            }
        }
        return tipoId;
    }

    public ArrayList<EntTipo> getTipos() {
        ArrayList<EntTipo> salida = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Asegúrate de que la consulta no tenga limitaciones
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_TIPO, null);

        if (c.moveToFirst()) {
            do {
                int codigoTipo = c.getInt(c.getColumnIndexOrThrow("codigoTipo"));
                String nombre = c.getString(c.getColumnIndexOrThrow("nombreTipo"));
                String descripcion = c.getString(c.getColumnIndexOrThrow("descripcionTipo"));

                EntTipo tipo = new EntTipo(codigoTipo, nombre, descripcion);
                salida.add(tipo);
            } while (c.moveToNext());
        }
        c.close();
        db.close();

        return salida;
    }


    //PARA OBTENER UN SOLO TIPO EN LA BASE DE DATOS
    public EntTipo getTipo(int codigoTipo) {
        EntTipo salida = null;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_TIPO + " WHERE " + KEY_COL_CODIGO_TIPO + " = " + codigoTipo, null);

        if (c.moveToFirst()) {
            int codigo = c.getInt(0);
            String nombre = c.getString(1);
            String descripcion = c.getString(2);
            salida = new EntTipo(codigo, nombre, descripcion);
        }
        return salida;
    }

    public int borrarTipo(int codigoTipo) {
        SQLiteDatabase db = this.getWritableDatabase();
        int borrados = 0;

        db.beginTransaction();
        try {
            borrados = db.delete(TABLE_TIPO, KEY_COL_CODIGO_TIPO + " = ?",
                    new String[]{String.valueOf(codigoTipo)});
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.d(TipoDatabaseHelper.class.getName(), "Error al intentar borrar tipo");
        } finally {
            db.endTransaction();
        }
        return borrados;
    }
}
