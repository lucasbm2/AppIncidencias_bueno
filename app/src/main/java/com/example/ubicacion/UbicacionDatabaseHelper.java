package com.example.ubicacion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.database.BBDDIncidencias;

import java.util.ArrayList;

import gestionincidencias.entidades.EntTipo;

public class UbicacionDatabaseHelper extends BBDDIncidencias {
    public UbicacionDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public long crearUbicacion(EntTipo tipo) {
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
            Log.d(UbicacionDatabaseHelper.class.getName(), "Error al anadir tipo");
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
                Log.d(UbicacionDatabaseHelper.class.getName(), "Error al actualizar tipo");
            } finally {
                db.endTransaction();
            }
        }
        return tipoId;
    }

    public ArrayList<EntTipo> getTipos() {
        ArrayList<EntTipo> salida = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_TIPO, null);

        if (c.moveToFirst()) {
            do {
                int codigoTipo = c.getInt(0);
                String nombre = c.getString(1);
                String descripcion = c.getString(2);
                EntTipo sala = new EntTipo(codigoTipo, nombre, descripcion);
                salida.add(sala);
            } while (c.moveToNext());
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
            Log.d(UbicacionDatabaseHelper.class.getName(), "Error al intentar borrar tipo");
        } finally {
            db.endTransaction();
        }
        return borrados;
    }
}
