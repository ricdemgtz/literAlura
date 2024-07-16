package com.alura.literalura.model;

public enum Categoria {
    ACCION("Action", "Acción"),
    ROMANCE("Romance", "Romance"),
    COMEDIA("Comedy", "Comedia"),
    CRIMEN("Crime", "Crimen"),
    DRAMA("Drama", "Drama"),
    AVENTURA("Adventure", "Aventura"),
    ANIME("Anime", "Anime"),
    DOCUMENTAL("Documentary", "Documental"),
    ANIMACION("Animation", "Animación");

    private String categoriaAPI;
    private String categoriaEspanol;

    Categoria(String categoriaOmdb, String categoriaEspanol) {
        this.categoriaAPI = categoriaOmdb;
        this.categoriaEspanol = categoriaEspanol;
    }

    public String getCategoriaAPI() {
        return categoriaAPI;
    }

    public String getCategoriaEspanol() {
        return categoriaEspanol;
    }

    public static Categoria fromString(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaAPI.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException(String.format("Ninguna categoria encontrada: %s", text));
    }

    public static Categoria fromEspanol(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaEspanol.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException(String.format("Ninguna categoria encontrada: %s", text));
    }

    public static Categoria fromTema(String tema) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaAPI.equalsIgnoreCase(tema)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException(String.format("Ninguna categoria encontrada para el tema: %s", tema));
    }

    @Override
    public String toString() {
        return String.format("Categoria{categoriaOmdb='%s', categoriaEspanol='%s'}", categoriaAPI, categoriaEspanol);
    }
}
