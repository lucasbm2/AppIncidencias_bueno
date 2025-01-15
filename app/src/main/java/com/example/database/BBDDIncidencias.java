package com.example.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BBDDIncidencias extends SQLiteOpenHelper {

    public BBDDIncidencias(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static final String TABLE_ELEMENTO = "elemento";
    public static final String TABLE_INCIDENCIA = "incidencia";
    public static final String TABLE_PRESTAMO = "prestamo";
    public static final String TABLE_ROL = "rol";
    public static final String TABLE_SALA = "sala";
    public static final String TABLE_TIPO = "tipo";
    public static final String TABLE_UBICACION = "ubicacion";
    public static final String TABLE_USUARIO = "usuario";

    //Columnas de la tabla sala
    public static final String KEY_COL_CODIGOSALA = "codigoSala";
    public static final String KEY_COL_NOMBRESALA = "nombreSala";
    public static final String KEY_COL_DESCRIPCIONSALA = "descripcionSala";

    //Columnas de la tabla tipo
    public static final String KEY_COL_CODIGO_TIPO = "codigoTipo";
    public static final String KEY_COL_NOMBRE_TIPO = "nombreTipo";
    public static final String KEY_COL_DESCRIPCION_TIPO = "descripcionTipo";

    //Columnas de la tabla elemento
    public static final String KEY_COL_CODIGO_ELEMENTO = "codigoElemento";
    public static final String KEY_COL_NOMBRE_ELEMENTO = "nombreElemento";
    public static final String KEY_COL_DESCRIPCION_ELEMENTO = "descripcionElemento";
    public static final String KEY_COL_CODIGO_TIPO_ELEMENTO = "codigoTipo";


    //Columnas de la tabla prestamo
    public static final String KEY_COL_CODIGO_PRESTAMO = "codigoPrestamo";
    public static final String KEY_COL_CODIGO_USUARIO_PRESTAMO = "codigoUsuario";
    public static final String KEY_COL_CODIGO_ELEMENTO_PRESTAMO = "codigoElemento";








    //SENTENCIAS DE SQL PARA BORRAR Y CREAR TABLA DE TIPO GUARDADAS EN VARIABLES
    private static final String BORRAR_TABLA_TIPO = "DROP TABLE IF EXISTS " + TABLE_TIPO;
    private static final String CREAR_TABLA_TIPO = "CREATE TABLE IF NOT EXISTS tipo " +
            "(codigoTipo INTEGER PRIMARY KEY AUTOINCREMENT, nombreTipo TEXT, descripcionTipo TEXT)";

    //SENTENCIAS DE SQL PARA BORRAR Y CREAR TABLA DE ELEMENTO GUARDADAS EN VARIABLES
    private static final String BORRAR_TABLA_ELEMENTO = "DROP TABLE IF EXISTS elemento";
    private static final String CREAR_TABLA_ELEMENTO = "CREATE TABLE IF NOT EXISTS elemento " +
            "( INTEGER PRIMARY KEY AUTOINCREMENT, nombreElemento TEXT, descripcionElemento TEXT, codigoTipo INTEGER)";
            //LLAMAR A LAS KEYS
    //
    //
    //
    //
    //
    //
    //SENTENCIAS DE SQL PARA BORRAR Y CREAR TABLA DE UBICACION GUARDADAS EN VARIABLES
    private static final String BORRAR_TABLA_UBICACION = "DROP TABLE IF EXISTS ubicacion";
    private static final String CREAR_TABLA_UBICACION = "CREATE TABLE IF NOT EXISTS ubicacion  " +
            "(codigoUbicacion INTEGER PRIMARY KEY AUTOINCREMENT, codigoSala INTEGER, descripcionUbicacion TEXT, fechaInicioUbicacion TEXT, fechaFinUbicacion TEXT, codigoUsuario INTEGER)";

    //SENTENCIAS DE SQL PARA BORRAR Y CREAR TABLA DE SALA GUARDADAS EN VARIABLES
    private static final String BORRAR_TABLA_SALA = "DROP TABLE IF EXISTS sala";
    private static final String CREAR_TABLA_SALA = "CREATE TABLE IF NOT EXISTS sala  " +
            "(codigoSala INTEGER PRIMARY KEY AUTOINCREMENT, nombreSala TEXT, descripcionSala TEXT)";

    //SENTENCIAS DE SQL PARA BORRAR Y CREAR TABLA DE PRESTAMO GUARDADAS EN VARIABLES
    private static final String BORRAR_TABLA_PRESTAMO = "DROP TABLE IF EXISTS prestamo";
    private static final String CREAR_TABLA_PRESTAMO = "CREATE TABLE IF NOT EXISTS prestamo  " +
            "(codigoPrestamo INTEGER PRIMARY KEY AUTOINCREMENT, codigoUsuario INTEGER, codigoElemento INTEGER, fechaInicioPrestamo TEXT , fechaFinPrestamo TEXT)";

    //SENTENCIAS DE SQL PARA BORRAR Y CREAR TABLA DE USUARIO GUARDADAS EN VARIABLES
    private static final String BORRAR_TABLA_USUARIO = "DROP TABLE IF EXISTS usuario";
    private static final String CREAR_TABLA_USUARIO = "CREATE TABLE IF NOT EXISTS usuario  " +
            "(codigoUsuario INTEGER PRIMARY KEY AUTOINCREMENT, nombreUsuario TEXT, correoUsuario TEXT, telefonoUsuario TEXT, passwordUsuario TEXT, codigoRol INTEGER)";

    //SENTENCIAS DE SQL PARA BORRAR Y CREAR TABLA DE ROL GUARDADAS EN VARIABLES
    private static final String BORRAR_TABLA_ROL = "DROP TABLE IF EXISTS rol";
    private static final String CREAR_TABLA_ROL = "CREATE TABLE IF NOT EXISTS rol  " +
            "(codigoRol INTEGER PRIMARY KEY AUTOINCREMENT, nombreRol TEXT, descripcionRol TEXT, nivelAccesoRol TEXT)";

    //SENTENCIAS DE SQL PARA BORRAR Y CREAR TABLA DE INCIDENCIA GUARDADAS EN VARIABLES
    private static final String BORRAR_TABLA_INCIDENCIA = "DROP TABLE IF EXISTS incidencia";
    private static final String CREAR_TABLA_INCIDENCIA = "CREATE TABLE IF NOT EXISTS incidencia  " +
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
