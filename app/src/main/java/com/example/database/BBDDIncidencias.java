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

        // Columnas de la tabla tipo
        public static final String KEY_COL_CODIGO_TIPO = "codigoTipo";
        public static final String KEY_COL_NOMBRE_TIPO = "nombreTipo";
        public static final String KEY_COL_DESCRIPCION_TIPO = "descripcionTipo";

        // Columnas de la tabla elemento
        public static final String KEY_COL_CODIGO_ELEMENTO = "codigoElemento";
        public static final String KEY_COL_NOMBRE_ELEMENTO = "nombreElemento";
        public static final String KEY_COL_DESCRIPCION_ELEMENTO = "descripcionElemento";
        public static final String KEY_COL_CODIGO_TIPO_ELEMENTO = "codigoTipo";

        // Columnas de la tabla usuario
        public static final String KEY_COL_CODIGO_USUARIO = "codigoUsuario";
        public static final String KEY_COL_NOMBRE_USUARIO = "nombreUsuario";
        public static final String KEY_COL_CORREO_USUARIO = "correoUsuario";
        public static final String KEY_COL_TELEFONO_USUARIO = "telefonoUsuario";
        public static final String KEY_COL_PASSWORD_USUARIO = "passwordUsuario";
        public static final String KEY_COL_CODIGO_ROL_USUARIO = "codigoRol";

        // Columnas de la tabla rol
        public static final String KEY_COL_CODIGO_ROL = "codigoRol";
        public static final String KEY_COL_NOMBRE_ROL = "nombreRol";
        public static final String KEY_COL_DESCRIPCION_ROL = "descripcionRol";
        public static final String KEY_COL_NIVEL_ACCESO_ROL = "nivelAccesoRol";

        // Columnas de la tabla prestamo
        public static final String KEY_COL_CODIGO_PRESTAMO = "codigoPrestamo";
        public static final String KEY_COL_CODIGO_USUARIO_PRESTAMO = "codigoUsuario";
        public static final String KEY_COL_CODIGO_ELEMENTO_PRESTAMO = "codigoElemento";
        public static final String KEY_COL_FECHA_INICIO_PRESTAMO = "fechaInicioPrestamo";
        public static final String KEY_COL_FECHA_FIN_PRESTAMO = "fechaFinPrestamo";

        // Columnas de la tabla sala
        public static final String KEY_COL_CODIGO_SALA = "codigoSala";
        public static final String KEY_COL_NOMBRE_SALA = "nombreSala";
        public static final String KEY_COL_DESCRIPCION_SALA = "descripcionSala";

        // Columnas de la tabla ubicacion
        public static final String KEY_COL_CODIGO_UBICACION = "codigoUbicacion";
        public static final String KEY_COL_CODIGO_SALA_UBICACION = "codigoSala";
        public static final String KEY_COL_DESCRIPCION_UBICACION = "descripcionUbicacion";
        public static final String KEY_COL_FECHA_INICIO_UBICACION = "fechaInicioUbicacion";
        public static final String KEY_COL_FECHA_FIN_UBICACION = "fechaFinUbicacion";
        public static final String KEY_COL_ID_ELEMENTO = "idElemento";

        // Columnas de la tabla incidencia
        public static final String KEY_COL_CODIGO_INCIDENCIA = "codigoIncidencia";
        public static final String KEY_COL_CODIGO_USUARIO_INCIDENCIA = "codigoUsuario";
        public static final String KEY_COL_CODIGO_ELEMENTO_INCIDENCIA = "codigoElemento";
        public static final String KEY_COL_DESCRIPCION_INCIDENCIA = "descripcionIncidencia";
        public static final String KEY_COL_FECHA_CREACION_INCIDENCIA = "fechaCreacion";


        // SENTENCIAS DE SQL PARA BORRAR Y CREAR TABLA DE TIPO GUARDADAS EN VARIABLES
        private static final String BORRAR_TABLA_TIPO = "DROP TABLE IF EXISTS " + TABLE_TIPO;
        private static final String CREAR_TABLA_TIPO = "CREATE TABLE IF NOT EXISTS " + TABLE_TIPO + " (" +
                KEY_COL_CODIGO_TIPO + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_COL_NOMBRE_TIPO + " TEXT, " +
                KEY_COL_DESCRIPCION_TIPO + " TEXT" +
                ");";

        // SENTENCIAS DE SQL PARA BORRAR Y CREAR TABLA DE ELEMENTO GUARDADAS EN VARIABLES
        private static final String BORRAR_TABLA_ELEMENTO = "DROP TABLE IF EXISTS " + TABLE_ELEMENTO;
        private static final String CREAR_TABLA_ELEMENTO = "CREATE TABLE IF NOT EXISTS " + TABLE_ELEMENTO + " (" +
                KEY_COL_CODIGO_ELEMENTO + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_COL_NOMBRE_ELEMENTO + " TEXT, " +
                KEY_COL_DESCRIPCION_ELEMENTO + " TEXT, " +
                KEY_COL_CODIGO_TIPO_ELEMENTO + " INTEGER, " +
                "FOREIGN KEY (" + KEY_COL_CODIGO_TIPO_ELEMENTO + ") REFERENCES " + TABLE_TIPO + "(" + KEY_COL_CODIGO_TIPO + ") " +
                "ON DELETE CASCADE ON UPDATE CASCADE" +
                ");";

        // SENTENCIAS DE SQL PARA BORRAR Y CREAR TABLA DE UBICACION GUARDADAS EN VARIABLES
        private static final String BORRAR_TABLA_UBICACION = "DROP TABLE IF EXISTS " + TABLE_UBICACION;
        private static final String CREAR_TABLA_UBICACION = "CREATE TABLE IF NOT EXISTS " + TABLE_UBICACION + " (" +
                KEY_COL_CODIGO_UBICACION + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_COL_CODIGO_SALA_UBICACION + " INTEGER, " +
                KEY_COL_DESCRIPCION_UBICACION + " TEXT, " +
                KEY_COL_FECHA_INICIO_UBICACION + " TEXT, " +
                KEY_COL_FECHA_FIN_UBICACION + " TEXT, " +
                KEY_COL_ID_ELEMENTO + " INTEGER, " +
                "FOREIGN KEY (" + KEY_COL_CODIGO_SALA_UBICACION + ") REFERENCES " + TABLE_SALA + "(" + KEY_COL_CODIGO_SALA + ") " +
                "ON DELETE CASCADE ON UPDATE CASCADE" +
                ");";

        // SENTENCIAS DE SQL PARA BORRAR Y CREAR TABLA DE SALA GUARDADAS EN VARIABLES
        private static final String BORRAR_TABLA_SALA = "DROP TABLE IF EXISTS " + TABLE_SALA;
        private static final String CREAR_TABLA_SALA = "CREATE TABLE IF NOT EXISTS " + TABLE_SALA + " (" +
                KEY_COL_CODIGO_SALA + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_COL_NOMBRE_SALA + " TEXT, " +
                KEY_COL_DESCRIPCION_SALA + " TEXT" +
                ");";

        // SENTENCIAS DE SQL PARA BORRAR Y CREAR TABLA DE PRESTAMO GUARDADAS EN VARIABLES
        private static final String BORRAR_TABLA_PRESTAMO = "DROP TABLE IF EXISTS " + TABLE_PRESTAMO;
        private static final String CREAR_TABLA_PRESTAMO = "CREATE TABLE IF NOT EXISTS " + TABLE_PRESTAMO + " (" +
                KEY_COL_CODIGO_PRESTAMO + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_COL_CODIGO_USUARIO_PRESTAMO + " INTEGER, " +
                KEY_COL_CODIGO_ELEMENTO_PRESTAMO + " INTEGER, " +
                KEY_COL_FECHA_INICIO_PRESTAMO + " TEXT, " +
                KEY_COL_FECHA_FIN_PRESTAMO + " TEXT, " +
                "FOREIGN KEY (" + KEY_COL_CODIGO_USUARIO_PRESTAMO + ") REFERENCES " + TABLE_USUARIO + "(" + KEY_COL_CODIGO_USUARIO + ") " +
                "ON DELETE CASCADE ON UPDATE CASCADE, " +
                "FOREIGN KEY (" + KEY_COL_CODIGO_ELEMENTO_PRESTAMO + ") REFERENCES " + TABLE_ELEMENTO + "(" + KEY_COL_CODIGO_ELEMENTO + ") " +
                "ON DELETE CASCADE ON UPDATE CASCADE" +
                ");";

        // SENTENCIAS DE SQL PARA BORRAR Y CREAR TABLA DE USUARIO GUARDADAS EN VARIABLES
        private static final String BORRAR_TABLA_USUARIO = "DROP TABLE IF EXISTS " + TABLE_USUARIO;
        private static final String CREAR_TABLA_USUARIO = "CREATE TABLE IF NOT EXISTS " + TABLE_USUARIO + " (" +
                KEY_COL_CODIGO_USUARIO + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_COL_NOMBRE_USUARIO + " TEXT, " +
                KEY_COL_CORREO_USUARIO + " TEXT UNIQUE, " +
                KEY_COL_TELEFONO_USUARIO + " TEXT, " +
                KEY_COL_PASSWORD_USUARIO + " TEXT, " +
                KEY_COL_CODIGO_ROL_USUARIO + " INTEGER, " +
                "FOREIGN KEY (" + KEY_COL_CODIGO_ROL_USUARIO + ") REFERENCES " + TABLE_ROL + "(" + KEY_COL_CODIGO_ROL + ") " +
                "ON DELETE CASCADE ON UPDATE CASCADE" +
                ");";

        // SENTENCIAS DE SQL PARA BORRAR Y CREAR TABLA DE ROL GUARDADAS EN VARIABLES
        private static final String BORRAR_TABLA_ROL = "DROP TABLE IF EXISTS " + TABLE_ROL;
        private static final String CREAR_TABLA_ROL = "CREATE TABLE IF NOT EXISTS " + TABLE_ROL + " (" +
                KEY_COL_CODIGO_ROL + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_COL_NOMBRE_ROL + " TEXT, " +
                KEY_COL_DESCRIPCION_ROL + " TEXT, " +
                KEY_COL_NIVEL_ACCESO_ROL + " TEXT" +
                ");";

        // SENTENCIAS DE SQL PARA BORRAR Y CREAR TABLA DE INCIDENCIA GUARDADAS EN VARIABLES
        private static final String BORRAR_TABLA_INCIDENCIA = "DROP TABLE IF EXISTS " + TABLE_INCIDENCIA;
        private static final String CREAR_TABLA_INCIDENCIA = "CREATE TABLE IF NOT EXISTS " + TABLE_INCIDENCIA + " (" +
                KEY_COL_CODIGO_INCIDENCIA + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_COL_CODIGO_USUARIO_INCIDENCIA + " INTEGER, " +
                KEY_COL_CODIGO_ELEMENTO_INCIDENCIA + " INTEGER, " +
                KEY_COL_DESCRIPCION_INCIDENCIA + " TEXT, " +
                KEY_COL_FECHA_CREACION_INCIDENCIA + " TEXT, " +
                "FOREIGN KEY (" + KEY_COL_CODIGO_USUARIO_INCIDENCIA + ") REFERENCES " + TABLE_USUARIO + "(" + KEY_COL_CODIGO_USUARIO + ") " +
                "ON DELETE CASCADE ON UPDATE CASCADE, " +
                "FOREIGN KEY (" + KEY_COL_CODIGO_ELEMENTO_INCIDENCIA + ") REFERENCES " + TABLE_ELEMENTO + "(" + KEY_COL_CODIGO_ELEMENTO + ") " +
                "ON DELETE CASCADE ON UPDATE CASCADE" +
                ");";

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

//        @Override
//        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//            // Este método se ejecuta cuando se intenta hacer un downgrade de la base de datos.
//            // Borra todas las tablas existentes y vuelve a crearlas.
//
//            db.execSQL(BORRAR_TABLA_TIPO);
//            db.execSQL(BORRAR_TABLA_ELEMENTO);
//            db.execSQL(BORRAR_TABLA_UBICACION);
//            db.execSQL(BORRAR_TABLA_SALA);
//            db.execSQL(BORRAR_TABLA_PRESTAMO);
//            db.execSQL(BORRAR_TABLA_USUARIO);
//            db.execSQL(BORRAR_TABLA_ROL);
//            db.execSQL(BORRAR_TABLA_INCIDENCIA);
//
//            // Llama al método onCreate para volver a crear todas las tablas
//            onCreate(db);
//        }



    }
