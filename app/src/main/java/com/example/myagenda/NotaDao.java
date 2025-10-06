package com.example.myagenda;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;


import java.util.ArrayList;
import java.util.List;
public class NotaDao {
        private DBHelper dbHelper;

        public NotaDao(Context context) {
            dbHelper = new DBHelper(context);
        }

        // Buscar notas por título
        public List<Nota> titulo(String titulo) {
            List<Nota> listaNotas = new ArrayList<>();
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            String[] columnas = {
                    aNotasContract.NotasEntry.COLUMN_NOTA_ID,
                    aNotasContract.NotasEntry.COLUMN_TITULO,
                    aNotasContract.NotasEntry.COLUMN_NOTA_TEXTO
            };

            String seleccion = aNotasContract.NotasEntry.COLUMN_TITULO + " LIKE ?";
            String[] argumentos = new String[]{"%" + titulo + "%"};

            Cursor cursor = db.query(
                    aNotasContract.NotasEntry.TABLE_NAME,
                    columnas,
                    seleccion,
                    argumentos,
                    null,
                    null,
                    aNotasContract.NotasEntry.COLUMN_TITULO + " ASC"
            );

            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(aNotasContract.NotasEntry.COLUMN_NOTA_ID));
                    String tit = cursor.getString(cursor.getColumnIndexOrThrow(aNotasContract.NotasEntry.COLUMN_TITULO));
                    String texto = cursor.getString(cursor.getColumnIndexOrThrow(aNotasContract.NotasEntry.COLUMN_NOTA_TEXTO));

                    listaNotas.add(new Nota(id, tit, texto));
                } while (cursor.moveToNext());
            }

            cursor.close();
            db.close();

            return listaNotas;

        }
       public boolean insertarNota(Nota nota) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues valores = new ContentValues();
          valores.put(aNotasContract.NotasEntry.COLUMN_TITULO, nota.getTitulo());
          valores.put(aNotasContract.NotasEntry.COLUMN_NOTA_TEXTO, nota.getTexto());

          long id = db.insert(aNotasContract.NotasEntry.TABLE_NAME, null, valores);
          db.close();
          return id != -1;
    }

}




