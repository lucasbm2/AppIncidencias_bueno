package com.example.rol;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.database.BBDDIncidencias;

import java.util.ArrayList;

import gestionincidencias.entidades.EntRol;

public class RolDatabaseHelper extends BBDDIncidencias {
    public RolDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public long crearRol(EntRol rol) {
        SQLiteDatabase db = getWritableDatabase();
        long rolID = -1;

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            if (rol.getCodigo() > 0) {
                values.put(KEY_COL_CODIGO_ROL, rol.getCodigo());
            }
            values.put(KEY_COL_NOMBRE_ROL, rol.getNombre());
            values.put(KEY_COL_DESCRIPCION_ROL, rol.getDescripcion());
            values.put(KEY_COL_NIVEL_ACCESO_ROL, rol.getNivel_acceso());

            rolID = db.insertOrThrow(TABLE_ROL, null, values);
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.e(RolDatabaseHelper.class.getName(), "Error al crear rol", e);
        } finally {
            db.endTransaction();
            db.close();
        }
        return rolID;
    }

    public long actualizarRol(EntRol rol) {
        SQLiteDatabase db = getWritableDatabase();
        long elementoID = -1;

        if (rol.getCodigo() > 0) {
            db.beginTransaction();
            try {
                ContentValues values = new ContentValues();
                if (rol.getCodigo() > 0) {
                    values.put(KEY_COL_CODIGO_ROL, rol.getCodigo());
                }
                values.put(KEY_COL_NOMBRE_ROL, rol.getNombre());
                values.put(KEY_COL_DESCRIPCION_ROL, rol.getDescripcion());
                values.put(KEY_COL_NIVEL_ACCESO_ROL, rol.getNivel_acceso());


                int rows = db.update(TABLE_ROL, values, KEY_COL_CODIGO_ROL + " = ?",
                        new String[]{String.valueOf(rol.getCodigo())});

                if (rows > 0) {
                    db.setTransactionSuccessful();
                    elementoID = rol.getCodigo();
                }
            } catch (Exception e) {
                Log.e(RolDatabaseHelper.class.getName(), "Error al actualizar rol", e);
            } finally {
                db.endTransaction();
                db.close();
            }
        }
        return elementoID;
    }

    public ArrayList<EntRol> getRoles() {
        ArrayList<EntRol> roles = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM " + TABLE_ROL, null);

            if (cursor.moveToFirst()) {
                do {
                    int codigo = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_COL_CODIGO_ROL));
                    String nombre = cursor.getString(cursor.getColumnIndexOrThrow(KEY_COL_NOMBRE_ROL));
                    String descripcion = cursor.getString(cursor.getColumnIndexOrThrow(KEY_COL_DESCRIPCION_ROL));
                    int nivelAcceso = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_COL_NIVEL_ACCESO_ROL));

                    EntRol rol = new EntRol(codigo, nombre, descripcion, nivelAcceso);

                    roles.add(rol);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(RolDatabaseHelper.class.getName(), "Error al obtener roles", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return roles;
    }

    public EntRol getRol(int codigo) {
        SQLiteDatabase db = getReadableDatabase();
        EntRol rol = null;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM " + TABLE_ROL + " WHERE " + KEY_COL_CODIGO_ROL + " = ?", new String[]{String.valueOf(codigo)});
            if (cursor.moveToFirst()) {
                int codigoRol = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_COL_CODIGO_ROL));
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow(KEY_COL_NOMBRE_ROL));
                String descripcion = cursor.getString(cursor.getColumnIndexOrThrow(KEY_COL_DESCRIPCION_ROL));
                int nivelAcceso = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_COL_NIVEL_ACCESO_ROL));
                rol = new EntRol(codigoRol, nombre, descripcion, nivelAcceso);
            }
        } catch (Exception e) {
            Log.e(RolDatabaseHelper.class.getName(), "Error al obtener rol", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return rol;
    }

    public int borrarRol(int codigo) {
        SQLiteDatabase db = getWritableDatabase();
        int borrados = 0;

        db.beginTransaction();
        try {
            borrados = db.delete(TABLE_ROL, KEY_COL_CODIGO_ROL + " = ?", new String[]{String.valueOf(codigo)});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e(RolDatabaseHelper.class.getName(), "Error al borrar rol", e);
        } finally {
            db.endTransaction();
            db.close();
        }
        return borrados;
    }

    public ArrayList<EntRol> buscadorRol(String texto) {
        ArrayList<EntRol> roles = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        try {
            String query = "SELECT * FROM " + TABLE_ROL + " WHERE " + KEY_COL_NOMBRE_ROL + " LIKE ?";
            cursor = db.rawQuery(query, new String[]{"%" + texto + "%"});

            if (cursor.moveToFirst()) {
                do {
                    int codigo = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_COL_CODIGO_ROL));
                    String nombre = cursor.getString(cursor.getColumnIndexOrThrow(KEY_COL_NOMBRE_ROL));
                    String descripcion = cursor.getString(cursor.getColumnIndexOrThrow(KEY_COL_DESCRIPCION_ROL));
                    int nivelAcceso = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_COL_NIVEL_ACCESO_ROL));
                    EntRol rol = new EntRol(codigo, nombre, descripcion, nivelAcceso);
                    roles.add(rol);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(RolDatabaseHelper.class.getName(), "Error al buscar roles", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return roles;
    }
}