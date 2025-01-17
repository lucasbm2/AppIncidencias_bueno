package com.example.sala;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.database.BBDDIncidencias;

import java.util.ArrayList;

import gestionincidencias.entidades.EntSala;

public class SalaDatabaseHelper extends BBDDIncidencias {
    public SalaDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public long crearSala(EntSala sala) {
        SQLiteDatabase db = this.getWritableDatabase();
        long salaId = -1;


        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            if (sala.getCodigoSala() >0) {
                values.put(KEY_COL_CODIGO_SALA, sala.getCodigoSala());
            }
            values.put(KEY_COL_NOMBRE_SALA, sala.getNombre());
            values.put(KEY_COL_DESCRIPCION_SALA, sala.getDescripcion());

            //si no existe y no se puede actualizar entonces lo creamos
            salaId = db.insertOrThrow(TABLE_SALA, null, values);
            db.setTransactionSuccessful();
            
        } catch (Exception e) {
            Log.d(SalaDatabaseHelper.class.getName(), "Error al anadir sala");
        } finally {
            db.endTransaction();
        }
        return salaId;
    }

    public long actualizarSala(EntSala sala) {
        SQLiteDatabase db = this.getWritableDatabase();
        long salaId = -1;

        db.beginTransaction();
        if (sala.getCodigoSala() > 0) {
            try {
                ContentValues values = new ContentValues();
                values.put(KEY_COL_CODIGO_SALA, sala.getCodigoSala());
                values.put(KEY_COL_NOMBRE_SALA, sala.getNombre());
                values.put(KEY_COL_DESCRIPCION_SALA, sala.getDescripcion());

                //Primero intento actualizar elemento concreto
                int rows = db.update(TABLE_SALA, values, KEY_COL_CODIGO_SALA + " = ?",
                        new String[]{String.valueOf(sala.getCodigoSala())});
                if (rows > 0) {
                    salaId = sala.getCodigoSala();
                    db.setTransactionSuccessful();
                }
            } catch (Exception e) {
                Log.d(SalaDatabaseHelper.class.getName(), "Error al actualizar sala");
            } finally {
                db.endTransaction();
            }
        }
        return salaId;
    }

    public ArrayList<EntSala> getSalas() {
        ArrayList<EntSala> salida = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_SALA, null);

        if (c.moveToFirst()) {
            do {
                int codigoSala = c.getInt(0);
                String nombre = c.getString(1);
                String descripcion = c.getString(2);
                EntSala sala = new EntSala(codigoSala, nombre, descripcion);
                salida.add(sala);
            } while (c.moveToNext());

        }

        return salida;
    }

    public int borrarSala(int codigoSala) {
        SQLiteDatabase db = this.getWritableDatabase();
        int borrados = 0;

        db.beginTransaction();
        try {
            borrados = db.delete(TABLE_SALA, KEY_COL_CODIGO_SALA + " = ?",
                    new String[]{String.valueOf(codigoSala)});
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.d(SalaDatabaseHelper.class.getName(), "Error al intentar borrar sala");
        } finally {
            db.endTransaction();
        }
        return borrados;
    }
}
