package com.example.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BBDDIncidencias extends SQLiteOpenHelper {

    public BBDDIncidencias(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //SENTENCIAS DE SQL PARA BORRAR Y CREAR TABLA DE TIPO GUARDADAS EN VARIABLES
    private static final String BORRAR_TABLA_TIPO = "DROP TABLE tipo";
    private static final String CREAR_TABLA_TIPO = "CREATE TABLE tipo " +
            "(codigoTipo INTEGER PRIMARY KEY AUTOINCREMENT, nombreTipo TEXT, descripcionTipo TEXT)";

    //SENTENCIAS DE SQL PARA BORRAR Y CREAR TABLA DE ELEMENTO GUARDADAS EN VARIABLES
    private static final String BORRAR_TABLA_ELEMENTO = "DROP TABLE elemento";
    private static final String CREAR_TABLA_ELEMENTO = "CREATE TABLE elemento " +
            "(codigoElemento INTEGER PRIMARY KEY AUTOINCREMENT, nombreElemento TEXT, descripcionElemento TEXT, codigoTipo INTEGER)";

    //SENTENCIAS DE SQL PARA BORRAR Y CREAR TABLA DE UBICACION GUARDADAS EN VARIABLES
    private static final String BORRAR_TABLA_UBICACION = "DROP TABLE ubicacion";
    private static final String CREAR_TABLA_UBICACION = "CREATE TABLE ubicacion  " +
            "(codigoUbicacion INTEGER PRIMARY KEY AUTOINCREMENT, codigoSala INTEGER, descripcionUbicacion TEXT, fechaInicioUbicacion TEXT, fechaFinUbicacion TEXT, codigoUsuario INTEGER)";

    //SENTENCIAS DE SQL PARA BORRAR Y CREAR TABLA DE SALA GUARDADAS EN VARIABLES
    private static final String BORRAR_TABLA_SALA = "DROP TABLE sala";
    private static final String CREAR_TABLA_SALA = "CREATE TABLE sala  " +
            "(codigoSala INTEGER PRIMARY KEY AUTOINCREMENT, nombreSala TEXT, descripcionSala TEXT)";

    //SENTENCIAS DE SQL PARA BORRAR Y CREAR TABLA DE PRESTAMO GUARDADAS EN VARIABLES
    private static final String BORRAR_TABLA_PRESTAMO = "DROP TABLE prestamo";
    private static final String CREAR_TABLA_PRESTAMO = "CREATE TABLE prestamo  " +
            "(codigoPrestamo INTEGER PRIMARY KEY AUTOINCREMENT, codigoUsuario INTEGER, codigoElemento INTEGER, fechaInicioPrestamo TEXT , fechaFinPrestamo TEXT)";

    //SENTENCIAS DE SQL PARA BORRAR Y CREAR TABLA DE USUARIO GUARDADAS EN VARIABLES
    private static final String BORRAR_TABLA_USUARIO = "DROP TABLE usuario";
    private static final String CREAR_TABLA_USUARIO = "CREATE TABLE usuario  " +
            "(codigoUsuario INTEGER PRIMARY KEY AUTOINCREMENT, nombreUsuario TEXT, correoUsuario TEXT, telefonoUsuario TEXT, passwordUsuario TEXT, codigoRol INTEGER)";

    //SENTENCIAS DE SQL PARA BORRAR Y CREAR TABLA DE ROL GUARDADAS EN VARIABLES
    private static final String BORRAR_TABLA_ROL = "DROP TABLE rol";
    private static final String CREAR_TABLA_ROL = "CREATE TABLE rol  " +
            "(codigoRol INTEGER PRIMARY KEY AUTOINCREMENT, nombreRol TEXT, descripcionRol TEXT, nivelAccesoRol TEXT)";

    //SENTENCIAS DE SQL PARA BORRAR Y CREAR TABLA DE INCIDENCIA GUARDADAS EN VARIABLES
    private static final String BORRAR_TABLA_INCIDENCIA = "DROP TABLE incidencia";
    private static final String CREAR_TABLA_INCIDENCIA = "CREATE TABLE incidencia  " +
            "(codigoIncidencia INTEGER PRIMARY KEY AUTOINCREMENT, codigoUsuario INTEGER, codigoElemento INTEGER, descripcionIncidencia TEXT, fechaCreacion TEXT)";

    //AL EJECUTAR SQLITE SE CREA LA TABLA TIPO
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREAR_TABLA_TIPO);
        sqLiteDatabase.execSQL(CREAR_TABLA_ELEMENTO);
        sqLiteDatabase.execSQL(CREAR_TABLA_UBICACION);
        sqLiteDatabase.execSQL(CREAR_TABLA_SALA);
        sqLiteDatabase.execSQL(CREAR_TABLA_PRESTAMO);
        sqLiteDatabase.execSQL(CREAR_TABLA_USUARIO);
        sqLiteDatabase.execSQL(CREAR_TABLA_ROL);
        sqLiteDatabase.execSQL(CREAR_TABLA_INCIDENCIA);
    }

    //AL EJECUTAR SQLITE SE BORRA LA TABLA TIPO Y SE CREA NUEVAMENTE
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(BORRAR_TABLA_TIPO);
        sqLiteDatabase.execSQL(BORRAR_TABLA_ELEMENTO);
        sqLiteDatabase.execSQL(BORRAR_TABLA_UBICACION);
        sqLiteDatabase.execSQL(BORRAR_TABLA_SALA);
        sqLiteDatabase.execSQL(BORRAR_TABLA_PRESTAMO);
        sqLiteDatabase.execSQL(BORRAR_TABLA_USUARIO);
        sqLiteDatabase.execSQL(BORRAR_TABLA_ROL);
        sqLiteDatabase.execSQL(BORRAR_TABLA_INCIDENCIA);
        onCreate(sqLiteDatabase);
    }

}
