package com.example.elemento;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.database.BBDDIncidencias;

import java.util.ArrayList;

import gestionincidencias.entidades.EntElemento;

public class ElementoDBHelper extends BBDDIncidencias {

    public ElementoDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public long crearElemento(EntElemento elemento) {
        SQLiteDatabase db = getWritableDatabase();
        long elementoID = -1;

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            if (elemento.getCodigoElemento() > 0) {
                values.put(KEY_COL_CODIGO_ELEMENTO, elemento.getCodigoElemento());
            }
            values.put(KEY_COL_NOMBRE_ELEMENTO, elemento.getNombre());
            values.put(KEY_COL_DESCRIPCION_ELEMENTO, elemento.getDescripcion());
            values.put(KEY_COL_CODIGO_TIPO_ELEMENTO, elemento.getIdTipo());

            elementoID = db.insertOrThrow(TABLE_ELEMENTO, null, values);
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.e(ElementoDBHelper.class.getName(), "Error al crear elemento", e);
        } finally {
            db.endTransaction();
        }
        return elementoID;
    }

    public long actualizarElemento(EntElemento elemento) {
        SQLiteDatabase db = getWritableDatabase();
        long elementoID = -1;

        if (elemento.getCodigoElemento() > 0) {
            db.beginTransaction();
            try {
                ContentValues values = new ContentValues();
                if (elemento.getCodigoElemento() > 0) {
                    values.put(KEY_COL_CODIGO_ELEMENTO, elemento.getCodigoElemento());
                }
                values.put(KEY_COL_NOMBRE_ELEMENTO, elemento.getNombre());
                values.put(KEY_COL_DESCRIPCION_ELEMENTO, elemento.getDescripcion());
                values.put(KEY_COL_CODIGO_TIPO_ELEMENTO, elemento.getIdTipo());

                int rows = db.update(TABLE_ELEMENTO, values, KEY_COL_CODIGO_ELEMENTO + " = ?",
                        new String[]{String.valueOf(elemento.getCodigoElemento())});

                if (rows > 0) {
                    db.setTransactionSuccessful();
                    elementoID = elemento.getCodigoElemento();
                }
            } catch (Exception e) {
                Log.e(ElementoDBHelper.class.getName(), "Error al actualizar elemento", e);
            } finally {
                db.endTransaction();
            }
        }
        return elementoID;
    }

    public ArrayList<EntElemento> getElementos() {
        ArrayList<EntElemento> elementos = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ELEMENTO, null);

        if (cursor.moveToFirst()) {
            do {
                int codigo = cursor.getInt(0);
                String nombre = cursor.getString(1);
                String descripcion = cursor.getString(2);
                int idTipo = cursor.getInt(3);

                EntElemento elemento = new EntElemento(codigo, nombre, descripcion, idTipo);

                elementos.add(elemento);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return elementos;
    }

    public EntElemento getElemento(int codigo) {
        Log.d("ElementoDBHelper", "Buscando elemento con codigo: " + codigo);
        SQLiteDatabase db = getReadableDatabase();
        EntElemento elemento = null;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM " + TABLE_ELEMENTO + " WHERE " + KEY_COL_CODIGO_ELEMENTO + " = ?", new String[]{String.valueOf(codigo)});
            Log.d("ElementoDBHelper", "Consulta ejecutada");
            if (cursor.moveToFirst()) {
                Log.d("ElementoDBHelper", "Elemento encontrado");
                int codigoElemento = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_COL_CODIGO_ELEMENTO));
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow(KEY_COL_NOMBRE_ELEMENTO));
                String descripcion = cursor.getString(cursor.getColumnIndexOrThrow(KEY_COL_DESCRIPCION_ELEMENTO));
                int idTipo = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_COL_CODIGO_TIPO_ELEMENTO));
                elemento = new EntElemento(codigoElemento, nombre, descripcion, idTipo);
            } else {
                Log.d("ElementoDBHelper", "Elemento NO encontrado");
            }
        } catch (Exception e) {
            Log.e(ElementoDBHelper.class.getName(), "Error al buscar elemento", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return elemento;
    }

    public int borrarElemento(int codigo) {
        SQLiteDatabase db = getWritableDatabase();
        int borrados = 0;

        db.beginTransaction();
        try {
            borrados = db.delete(TABLE_ELEMENTO, KEY_COL_CODIGO_ELEMENTO + " = ?", new String[]{String.valueOf(codigo)});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e(ElementoDBHelper.class.getName(), "Error al borrar elemento", e);
        } finally {
            db.endTransaction();
            db.close();
        }
        return borrados;
    }
}