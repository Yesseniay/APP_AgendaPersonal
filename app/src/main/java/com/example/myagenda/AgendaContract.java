package com.example.myagenda;

public final class AgendaContract {
    private AgendaContract() {}

    public static class ContactoEntry {
        public static final String TABLE_NAME = "Contactos";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_NAME = "nombre";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_NUMERO = "numero";
        public static final String COLUMN_NOTAS = "notas";
    }

}
