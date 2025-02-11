package com.example.prestamo;

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
import gestionincidencias.entidades.EntPrestamo;

public class PrestamoDatabaseHelper extends BBDDIncidencias {
    public PrestamoDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public SimpleDateFormat formateadorFecha = new SimpleDateFormat("yyyy-mm-dd HH:MM:SS");

    public long crearPrestamo(EntPrestamo prestamo) {
        SQLiteDatabase db = this.getWritableDatabase();
        long prestamoId = -1;

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            if (prestamo.getCodigoPrestamo() > 0) {
                values.put(KEY_COL_CODIGO_PRESTAMO, prestamo.getCodigoPrestamo());
            }
//            Log.d("IncidenciaDataBaseHelper", "Iniciando inserción de prestamo...");
//            Log.d("IncidenciaDataBaseHelper", "Valores para insertar: " + values.toString());
            values.put(KEY_COL_CODIGO_USUARIO_PRESTAMO, prestamo.getIdUsuario());
            values.put(KEY_COL_CODIGO_ELEMENTO_PRESTAMO, prestamo.getIdElemento());
            if (prestamo.getFechaInicio() != null) {
                values.put(KEY_COL_FECHA_INICIO_PRESTAMO, formateadorFecha.format(prestamo.getFechaInicio()));
            } else {
                values.put(KEY_COL_FECHA_INICIO_PRESTAMO, "");
            }
            if (prestamo.getFechaFin() != null) {
                values.put(KEY_COL_FECHA_FIN_PRESTAMO, formateadorFecha.format(prestamo.getFechaFin()));
            } else {
                values.put(KEY_COL_FECHA_FIN_PRESTAMO, "");
            }


            //si no existe y no se puede actualizar entonces lo creamos
            prestamoId = db.insertOrThrow(TABLE_PRESTAMO, null, values);
//            Log.d("IncidenciaDatabaseHelper", "Prestamo insertada con éxito. ID generado: " + prestamoId);
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.e("PrestamoDatabaseHelper", "Error al añadir prestamo: " + e.getMessage(), e);
        } finally {
            db.endTransaction();
        }
        return prestamoId;
    }

    public long actualizarPrestamo(EntPrestamo prestamo) {
        SQLiteDatabase db = this.getWritableDatabase();
        long prestamoId = -1;

        db.beginTransaction();
        if (prestamo.getCodigoPrestamo() > 0) {
            try {
                ContentValues values = new ContentValues();
//                Log.d("PrestamoDatabaseHelper", "Valores a insertar: " + values.toString());
                values.put(KEY_COL_CODIGO_PRESTAMO, prestamo.getCodigoPrestamo());
                values.put(KEY_COL_CODIGO_USUARIO_PRESTAMO, prestamo.getIdUsuario());
                values.put(KEY_COL_CODIGO_ELEMENTO_PRESTAMO, prestamo.getIdElemento());
                if (prestamo.getFechaInicio() != null) {
                    values.put(KEY_COL_FECHA_INICIO_PRESTAMO, formateadorFecha.format(prestamo.getFechaInicio()));
                } else {
                    values.put(KEY_COL_FECHA_INICIO_PRESTAMO, "");
                }
                if (prestamo.getFechaFin() != null) {
                    values.put(KEY_COL_FECHA_FIN_PRESTAMO, formateadorFecha.format(prestamo.getFechaFin()));
                } else {
                    values.put(KEY_COL_FECHA_FIN_PRESTAMO, "");
                }


                //Primero intento actualizar elemento concreto
                int rows = db.update(TABLE_PRESTAMO, values, KEY_COL_CODIGO_PRESTAMO + " = ?",
                        new String[]{String.valueOf(prestamo.getCodigoPrestamo())});
                if (rows > 0) {
                    prestamoId = prestamo.getCodigoPrestamo();
                    db.setTransactionSuccessful();
                }
            } catch (Exception e) {
                Log.e("PrestamoDatabaseHelper", "Error al modificar incidencia: " + e.getMessage(), e);
            } finally {
                db.endTransaction();
            }
        }
        return prestamoId;
    }

    public ArrayList<EntPrestamo> getPrestamos() throws ParseException {
        ArrayList<EntPrestamo> salida = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Consulta para obtener todas las ubicaciones
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_PRESTAMO, null);


        if (c.moveToFirst()) {
            do {
                int codigoPrestamo = c.getInt(0);
                int idUsuario = c.getInt(1);
                int idElemento = c.getInt(2);
                String fechaInicioString = c.getString(3);
                Date fechaInicioFormat = null;
                String fechaFinString = c.getString(4);
                Date fechaFinFormat = null;

                    try {
                        fechaInicioFormat = formateadorFecha.parse(String.valueOf(fechaInicioFormat));
                    } catch (ParseException e ) {
                        e.printStackTrace();
                    }

                    try {
                        fechaFinFormat = formateadorFecha.parse(String.valueOf(fechaFinFormat));
                    } catch (ParseException e ) {
                        e.printStackTrace();
                    }

                EntPrestamo prestamo = new EntPrestamo(codigoPrestamo, idUsuario, idElemento, fechaInicioFormat, fechaFinFormat);

                salida.add(prestamo);
            } while (c.moveToNext());
        }
        c.close();
        return salida;
    }

    public int borrarPrestamo(int codigoPrestamo) {
        SQLiteDatabase db = this.getWritableDatabase();
        int borrados = 0;

        db.beginTransaction();
        try {
            borrados = db.delete(TABLE_PRESTAMO, KEY_COL_CODIGO_PRESTAMO + " = ?",
                    new String[]{String.valueOf(codigoPrestamo)});
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.d(PrestamoDatabaseHelper.class.getName(), "Error al intentar borrar prestamo");
        } finally {
            db.endTransaction();
        }
        return borrados;
    }
}
