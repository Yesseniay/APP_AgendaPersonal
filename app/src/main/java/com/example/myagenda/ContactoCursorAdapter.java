// Archivo: ContactoCursorAdapter.java
package com.example.myagenda;

import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myagenda.AgendaContract.ContactoEntry;

public class ContactoCursorAdapter extends RecyclerView.Adapter<ContactoCursorAdapter.ContactosViewHolder> {

    private Cursor mCursor;

    public ContactoCursorAdapter(Cursor cursor) {
        this.mCursor = cursor;
    }

    // Define las referencias de los elementos de la vista de cada fila
    public static class ContactosViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNombre;
        public TextView tvDetalle;

        public ContactosViewHolder(View itemView) {
            super(itemView);
            // Asegúrate de que los IDs coincidan con tu list_item_contacto.xml
            tvNombre = itemView.findViewById(R.id.tv_nombre_contacto);
            tvDetalle = itemView.findViewById(R.id.tv_detalle_contacto);
        }
    }

    @NonNull
    @Override
    public ContactosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_item_contacto, parent, false);
        return new ContactosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactosViewHolder holder, int position) {
        // Mueve el cursor a la posición actual
        if (!mCursor.moveToPosition(position)) {
            return; // No debería suceder si getItemCount() es correcto
        }

        // Obtener datos del Cursor
        String nombre = mCursor.getString(mCursor.getColumnIndexOrThrow(ContactoEntry.COLUMN_NAME));
        String numero = mCursor.getString(mCursor.getColumnIndexOrThrow(ContactoEntry.COLUMN_NUMERO));
        String email = mCursor.getString(mCursor.getColumnIndexOrThrow(ContactoEntry.COLUMN_EMAIL));

        // Asignar datos a las vistas
        holder.tvNombre.setText(nombre);

        // Mostrar número si existe, si no, mostrar email
        if (numero != null && !numero.isEmpty()) {
            holder.tvDetalle.setText(numero);
        } else if (email != null && !email.isEmpty()){
            holder.tvDetalle.setText(email);
        } else {
            holder.tvDetalle.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return (mCursor == null) ? 0 : mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) {
            mCursor.close(); // Cierra el Cursor viejo
        }
        mCursor = newCursor;
        if (newCursor != null) {
            this.notifyDataSetChanged();
        }
    }
}