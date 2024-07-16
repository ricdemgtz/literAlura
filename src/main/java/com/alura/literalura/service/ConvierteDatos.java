package com.alura.literalura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class ConvierteDatos implements IConvierteDatos {

    private final ObjectMapper objectMapper;

    public ConvierteDatos(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public <T> T obetenerDatos(String json, Class<T> clase) {
        if (json == null || json.isEmpty()) {
            throw new IllegalArgumentException("El JSON proporcionado está vacío o nulo");
        }

        try {
            return objectMapper.readValue(json, clase);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error al procesar el JSON", e);
        }
    }
}
