package com.alura.literalura.repository;

import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Categoria;
import com.alura.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    Optional<Libro> findByTituloContainsIgnoreCase(String nombreSerie);

    List<Libro> findByGenero(Categoria categoria);

    @Query("SELECT l FROM Libro l WHERE l.autor.nombre ILIKE %:nombreAutor%")
    List<Libro> librosPorAutor(@Param("nombreAutor") String nombreAutor);

    @Query("SELECT DISTINCT a FROM Autor a WHERE a.anoNacimiento <= :ano AND (a.anoFallecimiento IS NULL OR a.anoFallecimiento > :ano)")
    List<Autor> findAutoresVivosEnAno(@Param("ano") int ano);

    List<Libro> findByIdiomasContainingIgnoreCase(String idioma);
}
