package com.example.incidencia;

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
import gestionincidencias.entidades.EntUbicacion;

public class IncidenciaDatabaseHelper extends BBDDIncidencias {
    public IncidenciaDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public SimpleDateFormat formateadorFecha = new SimpleDateFormat("yyyy-mm-dd HH:MM:SS");
    public long crearIncidencia(EntIncidencia incidencia) {
        SQLiteDatabase db = this.getWritableDatabase();
        long incidenciaId = -1;

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            if (incidencia.getCodigoIncidencia() >0) {
                values.put(KEY_COL_CODIGO_INCIDENCIA, incidencia.getCodigoIncidencia());
            }
//            Log.d("IncidenciaDataBaseHelper", "Iniciando inserción de ubicación...");
//            Log.d("IncidenciaDataBaseHelper", "Valores para insertar: " + values.toString());
            values.put(KEY_COL_CODIGO_USUARIO_INCIDENCIA, incidencia.getIdUsuarioCreacion());
            values.put(KEY_COL_CODIGO_ELEMENTO_INCIDENCIA, incidencia.getIdElemento());
            if (incidencia.getFechaCreacion() != null)  {
                values.put(KEY_COL_FECHA_CREACION_INCIDENCIA, formateadorFecha.format(incidencia.getFechaCreacion()));
            } else {
                values.put(KEY_COL_FECHA_CREACION_INCIDENCIA, "");
            }
            values.put(KEY_COL_DESCRIPCION_INCIDENCIA, incidencia.getDescripcion());

            //si no existe y no se puede actualizar entonces lo creamos
            incidenciaId = db.insertOrThrow(TABLE_INCIDENCIA, null, values);
//            Log.d("IncidenciaDatabaseHelper", "Incidencia insertada con éxito. ID generado: " + incidenciaId);
            db.setTransactionSuccessful();

        }catch (Exception e) {
//            Log.e("IncidenciaDatabaseHelper", "Error al añadir incidencia: " + e.getMessage(), e);
        }
        finally {
            db.endTransaction();
        }
        return incidenciaId;
    }

    public long actualizarIncidencia (EntIncidencia incidencia) {
        SQLiteDatabase db = this.getWritableDatabase();
        long incidenciaId = -1;

        db.beginTransaction();
        if (incidencia.getCodigoIncidencia() > 0) {
            try {
                ContentValues values = new ContentValues();
//                Log.d("IncidenciaDatabaseHelper", "Valores a insertar: " + values.toString());
                values.put(KEY_COL_CODIGO_INCIDENCIA, incidencia.getCodigoIncidencia());
                values.put(KEY_COL_CODIGO_USUARIO_INCIDENCIA, incidencia.getIdUsuarioCreacion());
                values.put(KEY_COL_ID_ELEMENTO, incidencia.getIdElemento());
                values.put(KEY_COL_FECHA_CREACION_INCIDENCIA, incidencia.getFechaCreacion().getDate());
                values.put(KEY_COL_DESCRIPCION_INCIDENCIA, incidencia.getDescripcion());



                //Primero intento actualizar elemento concreto
                int rows = db.update(TABLE_INCIDENCIA, values, KEY_COL_CODIGO_INCIDENCIA + " = ?",
                        new String[]{String.valueOf(incidencia.getCodigoIncidencia())});
                if (rows > 0) {
                    incidenciaId = incidencia.getCodigoIncidencia();
                    db.setTransactionSuccessful();
                }
            } catch (Exception e) {
                Log.e("IncidenciaDatabaseHelper", "Error al modificar incidencia: " + e.getMessage(), e);
            }
            finally {
                db.endTransaction();
            }
        }
        return incidenciaId;
    }

    public ArrayList<EntIncidencia> getIncidencias() throws ParseException {
        ArrayList<EntIncidencia> salida = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Consulta para obtener todas las ubicaciones
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_INCIDENCIA, null);


        if (c.moveToFirst()) {
            do {
                int codigoIncidencia = c.getInt(0);
                int idUsuarioCreacion = c.getInt(1);
                int idElemento = c.getInt(2);
                String fechaCreacionString = c.getString(3);
                Date fechaCreacionFormat = null;
                String descripcion = c.getString(4);

                if (fechaCreacionString != null && !fechaCreacionString.isEmpty()) {
                    fechaCreacionFormat = formateadorFecha.parse(String.valueOf(fechaCreacionFormat));
                }

                EntIncidencia incidencia = new EntIncidencia(codigoIncidencia, descripcion, idElemento, fechaCreacionFormat, idUsuarioCreacion);

                salida.add(incidencia);
            } while (c.moveToNext());
        }
        c.close();
        return salida;
    }

    public int borrarIncidencia(int codigoIncidencia) {
        SQLiteDatabase db = this.getWritableDatabase();
        int borrados = 0;

        db.beginTransaction();
        try {
            borrados = db.delete(TABLE_INCIDENCIA, KEY_COL_CODIGO_INCIDENCIA + " = ?",
                    new String[]{String.valueOf(codigoIncidencia)});
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.d(IncidenciaDatabaseHelper.class.getName(), "Error al intentar borrar incidencia");
        } finally {
            db.endTransaction();
        }
        return borrados;
    }
}
