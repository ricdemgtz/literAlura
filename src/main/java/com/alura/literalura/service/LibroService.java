package com.alura.literalura.service;

import com.alura.literalura.dto.AutorDTO;
import com.alura.literalura.dto.LibroDTO;
import com.alura.literalura.model.Libro;
import com.alura.literalura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LibroService {

    @Autowired
    private LibroRepository repository;

    public List<LibroDTO> obtenerTodosLosLibros() {
        List<Libro> libros = repository.findAll();
        return libros.stream()
                .map(libro -> new LibroDTO(libro.getId(), libro.getTitulo(), libro.getIdiomas(), libro.getDescargas(), libro.getGenero().toString())) // Obtener el nombre de la categoría como cadena
                .collect(Collectors.toList());
    }

    public LibroDTO obtenerPorId(Long id) {
        Optional<Libro> optionalLibro = repository.findById(id);
        return optionalLibro.map(libro -> new LibroDTO(libro.getId(), libro.getTitulo(), libro.getIdiomas(), libro.getDescargas(), libro.getGenero().toString())) // Obtener el nombre de la categoría como cadena
                .orElse(null);
    }

    public List<AutorDTO> obtenerAutoresPorLibro(Long id) {
        Optional<Libro> optionalLibro = repository.findById(id);
        return optionalLibro.map(libro -> libro.getAutores().stream()
                        .map(autor -> new AutorDTO(autor.getNombre(), autor.getAnoNacimiento(), autor.getAnoFallecimiento()))
                        .collect(Collectors.toList()))
                .orElse(null);
    }
}
