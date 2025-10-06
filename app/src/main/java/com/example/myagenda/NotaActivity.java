package com.example.myagenda;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotaActivity extends AppCompatActivity {

    private EditText buscador;
    private RecyclerView recyclerNotas;
    private NotaAdapter notaAdapter;
    private NotaDao notaDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nota);

        buscador = findViewById(R.id.Buscador);
        recyclerNotas = findViewById(R.id.Notas);

        notaDAO = new NotaDao(this);
        recyclerNotas.setLayoutManager(new LinearLayoutManager(this));

        // Cargar todas las notas
        mostrarNotas("");

        // Buscar mientras se escribe
        buscador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mostrarNotas(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void mostrarNotas(String filtro) {
        List<Nota> lista = notaDAO.titulo(filtro);
        notaAdapter = new NotaAdapter(lista);
        recyclerNotas.setAdapter(notaAdapter); // variable, no clase
    }
}





