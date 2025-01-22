package com.example.ubicacion;

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

import gestionincidencias.entidades.EntTipo;
import gestionincidencias.entidades.EntUbicacion;

public class UbicacionDatabaseHelper extends BBDDIncidencias {
    public UbicacionDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public SimpleDateFormat formateadorFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public long crearUbicacion(EntUbicacion ubicacion) {
        SQLiteDatabase db = this.getWritableDatabase();
        long ubicacionId = -1;



        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            if (ubicacion.getCodigoUbicacion() >0) {
                values.put(KEY_COL_CODIGO_UBICACION, ubicacion.getCodigoUbicacion());
            }
            Log.d("UbicacionDatabaseHelper", "Iniciando inserción de ubicación...");
            Log.d("UbicacionDatabaseHelper", "Valores para insertar: " + values.toString());
            values.put(KEY_COL_CODIGO_SALA_UBICACION, ubicacion.getIdSala());
            values.put(KEY_COL_ID_ELEMENTO, ubicacion.getIdElemento());
            values.put(KEY_COL_DESCRIPCION_UBICACION, ubicacion.getDescripcion());
            if (ubicacion.getFechaInicio() != null)  {
                values.put(KEY_COL_FECHA_INICIO_UBICACION, formateadorFecha.format(ubicacion.getFechaInicio()));
            } else {
                values.put(KEY_COL_FECHA_INICIO_UBICACION, "");
            }
            if (ubicacion.getFechaFin() != null) {
                values.put(KEY_COL_FECHA_FIN_UBICACION, formateadorFecha.format(ubicacion.getFechaFin()));
            } else {
                values.put(KEY_COL_FECHA_FIN_UBICACION, "");
            }

            //si no existe y no se puede actualizar entonces lo creamos
            ubicacionId = db.insertOrThrow(TABLE_UBICACION, null, values);
            Log.d("UbicacionDatabaseHelper", "Ubicación insertada con éxito. ID generado: " + ubicacionId);
            db.setTransactionSuccessful();
            
        }catch (Exception e) {
            Log.e("UbicacionDatabaseHelper", "Error al añadir ubicación: " + e.getMessage(), e);
        }
        finally {
            db.endTransaction();
            db.close();
        }
        return ubicacionId;
    }

    public long actualizarUbicacion (EntUbicacion ubicacion) {
        SQLiteDatabase db = this.getWritableDatabase();
        long ubicacionId = -1;

        db.beginTransaction();
        if (ubicacion.getCodigoUbicacion() > 0) {
            try {
                ContentValues values = new ContentValues();
                Log.d("UbicacionDatabaseHelper", "Valores a insertar: " + values.toString());
                values.put(KEY_COL_CODIGO_UBICACION, ubicacion.getCodigoUbicacion());
                values.put(KEY_COL_CODIGO_SALA_UBICACION, ubicacion.getIdSala());
                values.put(KEY_COL_CODIGO_ELEMENTO, ubicacion.getIdElemento());
                values.put(KEY_COL_DESCRIPCION_UBICACION, ubicacion.getDescripcion());
                values.put(KEY_COL_FECHA_INICIO_UBICACION, ubicacion.getFechaInicio().getDate());
                values.put(KEY_COL_FECHA_FIN_UBICACION, String.valueOf(ubicacion.getFechaFin()));



                //Primero intento actualizar elemento concreto
                int rows = db.update(TABLE_UBICACION, values, KEY_COL_CODIGO_UBICACION + " = ?",
                        new String[]{String.valueOf(ubicacion.getCodigoUbicacion())});
                if (rows > 0) {
                    ubicacionId = ubicacion.getCodigoUbicacion();
                    db.setTransactionSuccessful();
                }
            } catch (Exception e) {
                Log.e("UbicacionDatabaseHelper", "Error al modificar ubicacion: " + e.getMessage(), e);
            }
            finally {
                db.endTransaction();
                db.close();
            }
        }
        return ubicacionId;
    }

    public ArrayList<EntUbicacion> getUbicaciones() throws ParseException {
        ArrayList<EntUbicacion> salida = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Consulta para obtener todas las ubicaciones
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_UBICACION, null);


        if (c.moveToFirst()) {
            do {
                int codigoUbicacion = c.getInt(0);
                int idSala = c.getInt(1);
                int idElemento = c.getInt(2);
                String descripcion = c.getString(3);
                String fechaInicioString = c.getString(4);
                String fechaFinString = c.getString(5);
                Date fechaInicioFormat = null;
                Date fechaFinFormat = null;

                if (fechaInicioString != null && !fechaInicioString.isEmpty()) {
                    fechaInicioFormat = formateadorFecha.parse(fechaInicioString);
                }
                if (fechaFinString != null && !fechaFinString.isEmpty()) {
                    fechaFinFormat = formateadorFecha.parse(fechaFinString);
                }

                EntUbicacion ubicacion = new EntUbicacion(codigoUbicacion, idSala, idElemento, descripcion, fechaInicioFormat, fechaFinFormat);

                salida.add(ubicacion);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return salida;
    }

    //METODO PARA OBTENER UNA UBICACION
    public EntUbicacion getUbicacion(int codigoUbicacion) throws ParseException {
        EntUbicacion ubicacion = null;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_UBICACION + " WHERE " + KEY_COL_CODIGO_UBICACION + " = " + codigoUbicacion, null);

        if (c.moveToFirst()) {
            int idSala = c.getInt(1);
            int idElemento = c.getInt(2);
            String descripcion = c.getString(3);
            String fechaInicioString = c.getString(4);
            String fechaFinString = c.getString(5);
            Date fechaInicioFormat = null;
            Date fechaFinFormat = null;

            if (fechaInicioString != null && !fechaInicioString.isEmpty()) {
                fechaInicioFormat = formateadorFecha.parse(fechaInicioString);
            }
            if (fechaFinString != null && !fechaFinString.isEmpty()) {
                fechaFinFormat = formateadorFecha.parse(fechaFinString);
            }

            ubicacion = new EntUbicacion(codigoUbicacion, idSala, idElemento, descripcion, fechaInicioFormat, fechaFinFormat);
        }
        c.close();
        db.close();
        return ubicacion;
    }

    public int borrarUbicacion(int codigoUbicacion) {
        SQLiteDatabase db = this.getWritableDatabase();
        int borrados = 0;

        db.beginTransaction();
        try {
            borrados = db.delete(TABLE_UBICACION, KEY_COL_CODIGO_UBICACION + " = ?",
                    new String[]{String.valueOf(codigoUbicacion)});
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.d(UbicacionDatabaseHelper.class.getName(), "Error al intentar borrar ubicacion");
        } finally {
            db.endTransaction();
        }
        return borrados;
    }
}
