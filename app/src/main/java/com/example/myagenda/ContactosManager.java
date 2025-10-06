package com.example.myagenda;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.myagenda.AgendaContract.ContactoEntry;

public class ContactosManager {

    private final DBHelper dbHelper;

    public ContactosManager(Context context) {
        dbHelper = new DBHelper(context);
    }

    public long agregarContacto(String nombre, String numero, String email, String notas) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ContactoEntry.COLUMN_NAME, nombre);
        values.put(ContactoEntry.COLUMN_NUMERO, numero);
        values.put(ContactoEntry.COLUMN_EMAIL, email);
        values.put(ContactoEntry.COLUMN_NOTAS, notas);

        long newRowId = db.insert(
                ContactoEntry.TABLE_NAME,
                null,
                values
        );

        db.close();

        return newRowId;
    }

    public Cursor buscarContactos(String textoBusqueda) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                ContactoEntry.COLUMN_ID,
                ContactoEntry.COLUMN_NAME,
                ContactoEntry.COLUMN_NUMERO,
                ContactoEntry.COLUMN_EMAIL
        };

        String selection = null;
        String[] selectionArgs = null;

        if (textoBusqueda != null && !textoBusqueda.isEmpty()) {
            selection =
                    ContactoEntry.COLUMN_NAME + " LIKE ? OR " +
                            ContactoEntry.COLUMN_NUMERO + " LIKE ? OR " +
                            ContactoEntry.COLUMN_EMAIL + " LIKE ?";

            String likePattern = "%" + textoBusqueda + "%";
            selectionArgs = new String[] {
                    likePattern,
                    likePattern,
                    likePattern
            };
        }

        Cursor cursor = db.query(
                ContactoEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                ContactoEntry.COLUMN_NAME + " ASC"
        );

        return cursor;
    }

    public Cursor obtenerTodosLosContactos() {
        return buscarContactos("");
    }
}
