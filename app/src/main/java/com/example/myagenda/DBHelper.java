package com.example.myagenda;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "AgendaPersonal.db";
    private static final String SQL_CREATE_CONTACTOS =
            "CREATE TABLE " + AgendaContract.ContactoEntry.TABLE_NAME + " (" +
                    AgendaContract.ContactoEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    AgendaContract.ContactoEntry.COLUMN_NAME + " TEXT," +
                    AgendaContract.ContactoEntry.COLUMN_EMAIL + " TEXT UNIQUE NOT NULL," +
                    AgendaContract.ContactoEntry.COLUMN_NUMERO + " TEXT UNIQUE NOT NULL," +
                    AgendaContract.ContactoEntry.COLUMN_NOTAS + " TEXT)";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_CONTACTOS);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + AgendaContract.ContactoEntry.TABLE_NAME);
        onCreate(db);
    }
}