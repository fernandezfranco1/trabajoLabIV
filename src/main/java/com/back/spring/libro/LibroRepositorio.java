package com.back.spring.libro;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro, Long> {


    //Buscamos a traves del titulo del libro para ver si esta registrado
    Optional<Libro> findLibroByTitulo(String titulo);

}