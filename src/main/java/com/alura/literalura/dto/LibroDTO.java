package com.alura.literalura.dto;

import com.alura.literalura.model.Categoria;

import java.util.List;

public record LibroDTO(
        Long id,
        String titulo,
        List<String> idiomas, // Cambiado a List<String>
        Integer descargas,
        String genero // Cambiado a String
) {
}
