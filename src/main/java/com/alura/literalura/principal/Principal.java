package com.alura.literalura.principal;

import com.alura.literalura.dto.AutorDTO;
import com.alura.literalura.model.Autor;
import com.alura.literalura.model.DatosLibro;
import com.alura.literalura.model.Libro;
import com.alura.literalura.repository.LibroRepository;
import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.ConvierteDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class Principal {
    private final Scanner teclado = new Scanner(System.in);
    private final ConsumoAPI consumoAPI = new ConsumoAPI();
    private final ConvierteDatos conversor;

    private final LibroRepository repositorio;

    @Autowired
    public Principal(ConvierteDatos conversor, LibroRepository repositorio) {
        this.conversor = conversor;
        this.repositorio = repositorio;
    }

    public void muestraElMenu() {
        int opcion = -1;
        while (opcion != 0) {
            mostrarMenu();
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                case 1:
                    buscarLibroPorNombre();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosEnAno();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        }
    }

    private void mostrarMenu() {
        String menu = """
                1 - Buscar libro por nombre
                2 - Listar libros registrados
                3 - Listar autores registrados
                4 - Listar autores vivos en un determinado año
                5 - Listar libros por idioma
                0 - Salir
                """;
        System.out.println(menu);
    }

    private void buscarLibroPorNombre() {
        System.out.println("Escribe el nombre del libro que deseas buscar:");
        String nombreLibro = teclado.nextLine();

        try {
            String url = "https://gutendex.com/books?search=" + URLEncoder.encode(nombreLibro, StandardCharsets.UTF_8);
            System.out.println("URL de consulta: " + url);

            String json = consumoAPI.obtenerDatos(url);
            System.out.println("JSON recibido: " + json);

            if (json == null || json.isEmpty()) {
                throw new IllegalArgumentException("El JSON recibido está vacío o nulo");
            }

            DatosLibro[] datosLibros = conversor.obetenerDatos(json, DatosLibro[].class);

            if (datosLibros != null && datosLibros.length > 0) {
                for (DatosLibro datosLibro : datosLibros) {
                    System.out.println("Libro encontrado:");
                    System.out.println(datosLibro.toString());
                    guardarLibroEnBaseDatos(datosLibro);
                }
            } else {
                System.out.println("Libro no encontrado.");
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Error al buscar el libro: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error general al buscar el libro:");
            e.printStackTrace();
        }
    }

    private void guardarLibroEnBaseDatos(DatosLibro datosLibro) {
        // Aquí podrías implementar una lógica para verificar si el libro ya existe en la base de datos
        // antes de guardarlo nuevamente.
        repositorio.save(new Libro(datosLibro));
        System.out.println("Libro guardado en la base de datos.");
    }

    private void listarLibrosRegistrados() {
        List<Libro> libros = repositorio.findAll();
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
        } else {
            libros.forEach(libro -> System.out.println(libro.toString()));
        }
    }

    private void listarAutoresRegistrados() {
        List<AutorDTO> autoresDTO = repositorio.findAll().stream()
                .flatMap(libro -> libro.getAutores().stream())
                .map(autor -> new AutorDTO(autor.getNombre(), autor.getAnoNacimiento(), autor.getAnoFallecimiento()))
                .collect(Collectors.toList());

        if (autoresDTO.isEmpty()) {
            System.out.println("No hay autores registrados.");
        } else {
            autoresDTO.forEach(autor -> System.out.println(autor.toString()));
        }
    }

    private void listarAutoresVivosEnAno() {
        System.out.println("Escribe el año para listar autores vivos:");
        int ano = teclado.nextInt();
        teclado.nextLine();

        List<Autor> autores = repositorio.findAutoresVivosEnAno(ano);
        if (autores.isEmpty()) {
            System.out.println("No hay autores vivos en el año " + ano);
        } else {
            autores.forEach(autor -> System.out.println(autor.toString()));
        }
    }

    private void listarLibrosPorIdioma() {
        System.out.println("Escribe el idioma para listar libros:");
        String idioma = teclado.nextLine();

        List<Libro> libros = repositorio.findByIdiomasContainingIgnoreCase(idioma);
        if (libros.isEmpty()) {
            System.out.println("No hay libros en el idioma " + idioma);
        } else {
            libros.forEach(libro -> System.out.println(libro.toString()));
        }
    }
}
