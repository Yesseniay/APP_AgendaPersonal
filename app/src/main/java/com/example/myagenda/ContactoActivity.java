package com.example.myagenda;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

// Importar las constantes de la BD
import static com.example.myagenda.AgendaContract.ContactoEntry;

public class ContactoActivity extends AppCompatActivity {

    // Componentes de la interfaz
    private RecyclerView recyclerView;
    private EditText etSearchInput;
    private ImageButton btnVolver;
    private ImageButton btnSearch;

    // Clases de lógica y datos
    private ContactosManager manager;
    private ContactoCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Usar el layout que corregimos con ConstraintLayout
        setContentView(R.layout.activity_contacto);

        // 1. Inicializar componentes y Manager
        etSearchInput = findViewById(R.id.et_search_input);
        btnSearch = findViewById(R.id.btnn_guardar);
        btnVolver = findViewById(R.id.btn_volver);
        recyclerView = findViewById(R.id.contenedor);

        manager = new ContactosManager(this);

        // 2. Configurar RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 3. Inicializar el adaptador con todos los contactos (Cursor inicial)
        adapter = new ContactoCursorAdapter(manager.obtenerTodosLosContactos());
        recyclerView.setAdapter(adapter);

        // 4. Configurar Listeners
        configurarListeners();
    }

    // Se llama cada vez que se regresa a esta Activity
    @Override
    protected void onResume() {
        super.onResume();
        // Recarga la lista para mostrar nuevos contactos (sin cambiar el filtro actual)
        realizarBusqueda();
    }

    private void configurarListeners() {
        // Listener del botón de búsqueda
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realizarBusqueda();
                // Opcional: Ocultar el teclado al buscar
                ocultarTeclado(v);
            }
        });

        // Listener del botón de volver
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Cierra esta Activity y regresa a la anterior (MainActivity)
            }
        });

        // OPCIONAL: Implementar búsqueda en tiempo real (más interactivo)
        // Puedes agregar un TextWatcher aquí si deseas que la lista se filtre al escribir.
        /*
        etSearchInput.addTextChangedListener(new TextWatcher() {
            // ... implementar métodos ...
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                realizarBusqueda();
            }
        });
        */
    }

    /**
     * Obtiene el texto del campo de búsqueda y actualiza el RecyclerView.
     */
    private void realizarBusqueda() {
        String textoBusqueda = etSearchInput.getText().toString().trim();

        // 1. Obtener el nuevo Cursor filtrado o todos los contactos si el texto está vacío
        Cursor nuevoCursor = manager.buscarContactos(textoBusqueda);

        // 2. Actualizar el adaptador de forma segura (cierra el Cursor viejo)
        adapter.swapCursor(nuevoCursor);
    }

    /**
     * Método de limpieza de memoria. Cierra el Cursor.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (adapter != null) {
            // Pasamos null para asegurarnos de que el adaptador cierre el Cursor que tiene
            adapter.swapCursor(null);
        }
    }

    /**
     * Oculta el teclado virtual.
     */
    private void ocultarTeclado(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}