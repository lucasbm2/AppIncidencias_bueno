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
            Log.d(ElementoDBHelper.class.getName(), e.getMessage());
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
                Log.d(ElementoDBHelper.class.getName(), e.getMessage());
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
            }while (cursor.moveToNext());
        }
        return elementos;
    }

    public int borrarElemento(int codigo){
        SQLiteDatabase db = getWritableDatabase();
        int borrados = 0;

        db.beginTransaction();
        try {
            borrados = db.delete("elemento", "codigo = ?", new String[]{String.valueOf(codigo)});
            db.setTransactionSuccessful();
        }catch (Exception e){
            Log.d(ElementoDBHelper.class.getName(), e.getMessage());
        }finally {
            db.endTransaction();
        }
        return borrados;
    }

}
