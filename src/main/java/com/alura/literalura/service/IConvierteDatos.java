package com.alura.literalura.service;

public interface IConvierteDatos {
    <T> T obetenerDatos(String json, Class<T> clase);
}
