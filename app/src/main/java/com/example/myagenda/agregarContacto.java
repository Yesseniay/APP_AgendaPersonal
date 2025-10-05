
package com.example.myagenda;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import static com.example.myagenda.AgendaContract.ContactoEntry;

public class agregarContacto extends AppCompatActivity {
    public EditText etNombre, etNumero, etEmail, etNotas;
    public DBHelper dbHelper;
    public SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_contacto);

        // inicializacion de variables
        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();
        etNombre = findViewById(R.id.et_nombre);
        etNumero = findViewById(R.id.et_numero);
        etEmail = findViewById(R.id.et_email);
        etNotas = findViewById(R.id.et_notas);
        Button btnGuardar = findViewById(R.id.btn_guardar);

        // Listener para el botón de guardar
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarContacto();
            }
        });
    }

    private void guardarContacto() {
        // 1. Obtener y limpiar los datos del formulario
        String nombre = etNombre.getText().toString().trim();
        String numero = etNumero.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String notas = etNotas.getText().toString().trim();

        // 2. Validación de datos esenciales
        if (nombre.isEmpty()) {
            Toast.makeText(this, "El nombre es obligatorio.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (numero.isEmpty() && email.isEmpty()) {
            Toast.makeText(this, "Debe ingresar al menos un número o email.", Toast.LENGTH_LONG).show();
            return;
        }

        // 3. Preparar los datos usando ContentValues
        ContentValues values = new ContentValues();
        values.put(ContactoEntry.COLUMN_NAME, nombre);
        values.put(ContactoEntry.COLUMN_NUMERO, numero);
        values.put(ContactoEntry.COLUMN_EMAIL, email);
        values.put(ContactoEntry.COLUMN_NOTAS, notas);

        // 4. Ejecutar la inserción y capturar el ID de la nueva fila
        long newRowId = db.insert(
                ContactoEntry.TABLE_NAME,
                null,
                values
        );

        // 5. Cerrar la conexión y manejar el resultado
        db.close();

        if (newRowId != -1) {
            Toast.makeText(this, "Contacto guardado.", Toast.LENGTH_SHORT).show();
            finish(); // Cierra esta actividad y regresa a la anterior
        } else {
            // El error -1 indica que la inserción falló (probablemente por un email/número duplicado, ya que son UNIQUE)
            Toast.makeText(this, "Error al guardar.", Toast.LENGTH_SHORT).show();
        }
    }
}