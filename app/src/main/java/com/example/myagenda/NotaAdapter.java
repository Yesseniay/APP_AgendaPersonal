package com.example.myagenda;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
    public class NotaAdapter extends RecyclerView.Adapter<NotaAdapter.NotaViewHolder> {

        private List<Nota> listaNotas;

        public NotaAdapter(List<Nota> listaNotas) {
            this.listaNotas = listaNotas;
        }

        @NonNull
        @Override
        public NotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View vista = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_nota, parent, false); // Layout del item
            return new NotaViewHolder(vista);
        }

        @Override
        public void onBindViewHolder(@NonNull NotaViewHolder holder, int position) {
            Nota nota = listaNotas.get(position);
            holder.txtTituloNota.setText(nota.getTitulo());
            holder.txtTextoNota.setText(nota.getTexto());
        }

        @Override
        public int getItemCount() {
            return listaNotas.size();
        }

        public static class NotaViewHolder extends RecyclerView.ViewHolder {
            TextView txtTituloNota, txtTextoNota;

            public NotaViewHolder(@NonNull View itemView) {
                super(itemView);
                txtTituloNota = itemView.findViewById(R.id.txtTituloNota);
                txtTextoNota = itemView.findViewById(R.id.txtTextoNota);
            }
        }
    }


