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
import java.util.Locale;

import gestionincidencias.entidades.EntIncidencia;

public class IncidenciaDatabaseHelper extends BBDDIncidencias {
    public IncidenciaDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public SimpleDateFormat formateadorFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

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

    public void actualizarIncidencia(EntIncidencia incidencia) {
        SQLiteDatabase db = this.getWritableDatabase();
        long incidenciaId = -1;

        try {
            ContentValues values = new ContentValues();
            values.put(KEY_COL_CODIGO_USUARIO_INCIDENCIA, incidencia.getIdUsuarioCreacion());
            values.put(KEY_COL_CODIGO_ELEMENTO_INCIDENCIA, incidencia.getIdElemento());
            values.put(KEY_COL_DESCRIPCION_INCIDENCIA, incidencia.getDescripcion());

            SimpleDateFormat dateFormatDB = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            values.put(KEY_COL_FECHA_CREACION_INCIDENCIA, incidencia.getFechaCreacion() != null ? dateFormatDB.format(incidencia.getFechaCreacion()) : "0000-00-00 00:00:00");

            // 🔹 Verificar antes de actualizar
            Log.d("ActualizarIncidencia", "Actualizando incidencia con ID: " + incidencia.getCodigoIncidencia());
            Log.d("ActualizarIncidencia", "Valores: " + values.toString());

            int rows = db.update(TABLE_INCIDENCIA, values, KEY_COL_CODIGO_INCIDENCIA + " = ?",
                    new String[]{String.valueOf(incidencia.getCodigoIncidencia())});

            if (rows > 0) {
                incidenciaId = incidencia.getCodigoIncidencia();
                Log.d("IncidenciaDatabaseHelper", "Incidencia actualizada correctamente: " + incidenciaId);
            } else {
                Log.e("IncidenciaDatabaseHelper", "No se actualizó la incidencia en la base de datos. ¿Existe el ID?");
            }
        } catch (Exception e) {
            Log.e("IncidenciaDatabaseHelper", "Error al modificar incidencia: " + e.getMessage(), e);
        } finally {
            db.close();
        }Log.d("ActualizarIncidencia", "Antes de actualizar -> ID: " + incidencia.getCodigoIncidencia() +
                ", Usuario: " + incidencia.getIdUsuarioCreacion() +
                ", Elemento: " + incidencia.getIdElemento() +
                ", Descripción: " + incidencia.getDescripcion() +
                ", Fecha: " + incidencia.getFechaCreacion());
    }




    public ArrayList<EntIncidencia> getIncidencias() {
        ArrayList<EntIncidencia> salida = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Consulta para obtener todas las ubicaciones
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_INCIDENCIA, null);


        if (c.moveToFirst()) {
            do {
                int codigoIncidencia = c.getInt(0);
                int idUsuarioCreacion = c.getInt(1);
                int idElemento = c.getInt(2);
                String descripcion = c.getString(3);  // Asegúrate de que la descripción es correcta
                String fechaCreacionString = c.getString(4);
                Date fechaCreacionFormat = null;

                // Verificar si la fecha es válida antes de convertirla
                if (fechaCreacionString != null && !fechaCreacionString.isEmpty()) {
                    try {
                        fechaCreacionFormat = formateadorFecha.parse(fechaCreacionString);
                    } catch (ParseException e) {
                        e.printStackTrace();
                        fechaCreacionFormat = null;  // Si hay error, asignamos null en lugar de una fecha errónea
                    }
                }

                // Verificar si la descripción y la fecha son correctas
                Log.d("DatabaseHelper", "Descripción: " + descripcion);
                Log.d("DatabaseHelper", "Fecha Creación: " + fechaCreacionString);

                EntIncidencia incidencia = new EntIncidencia(codigoIncidencia, descripcion, idElemento, fechaCreacionFormat, idUsuarioCreacion);
                salida.add(incidencia);

            } while (c.moveToNext());
        }
        c.close();
        return salida;
    }

    public EntIncidencia getIncidencia(int codigoIncidencia) {
        SQLiteDatabase db = this.getReadableDatabase();
        EntIncidencia incidencia = null;

        // Consulta para obtener todas las ubicaciones
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_INCIDENCIA + " WHERE " + KEY_COL_CODIGO_INCIDENCIA + " = " + codigoIncidencia, null);

        if (c.moveToFirst()) {
            int codigo = c.getInt(0);
            int idUsuarioCreacion = c.getInt(1);
            int idElemento = c.getInt(2);
            String descripcion = c.getString(3);  // Asegúrate de que la descripción es correcta
            String fechaCreacionString = c.getString(4);
            Date fechaCreacionFormat = null;

            if (fechaCreacionString != null && !fechaCreacionString.isEmpty()) {
                try {
                    fechaCreacionFormat = formateadorFecha.parse(fechaCreacionString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            try {
                fechaCreacionFormat = formateadorFecha.parse(fechaCreacionString);
            } catch (ParseException e ) {
                e.printStackTrace();
            }

            incidencia = new EntIncidencia(codigo, descripcion, idElemento, fechaCreacionFormat, idUsuarioCreacion);
        }
        c.close();
        return incidencia;
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
