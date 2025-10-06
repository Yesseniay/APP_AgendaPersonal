package com.example.myagenda;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
public class escribirNotas {

    public class ActivityNoEscrActivity extends AppCompatActivity {

        private EditText edtTitulo, edtContenido;
        private Button btnGuardar;
        private NotaDao notaDAO;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activitynoescr);

            edtTitulo = findViewById(R.id.Titulo);
            edtContenido = findViewById(R.id.Contenido);
            btnGuardar = findViewById(R.id.btnGuardar);

            notaDAO = new NotaDao(this);

            btnGuardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    guardarNota();
                }
            });
        }

        private void guardarNota() {
            String titulo = edtTitulo.getText().toString().trim();
            String contenido = edtContenido.getText().toString().trim();

            if (TextUtils.isEmpty(titulo) || TextUtils.isEmpty(contenido)) {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }


            Nota nuevaNota = new Nota(0, titulo, contenido);

            // Guardar en DB
            boolean exito = notaDAO.insertarNota(nuevaNota); // Necesitamos este método en NotaDAO

            if (exito) {
                Toast.makeText(this, "Nota guardada", Toast.LENGTH_SHORT).show();
                finish(); // cerrar activity y volver a la lista
            } else {
                Toast.makeText(this, "Error al guardar la nota", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
