package com.back.spring.libro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class LibroServicio {
    HashMap<String, Object> datos;

    private final LibroRepositorio libroRepositorio;

    @Autowired
    public LibroServicio(LibroRepositorio libroRepositorio){

        this.libroRepositorio = libroRepositorio;
    }
    public List<Libro> getLibros(){

        return this.libroRepositorio.findAll();
    }

    public ResponseEntity<Object> newLibro(Libro libro) {
        Optional<Libro> libroExistente = libroRepositorio.findLibroByTitulo(libro.getTitulo());
        datos = new HashMap<>();

        if (libroExistente.isPresent() && libro.getId() == null) {
            datos.put("Error!", true);
            datos.put("message", "Ya existe un libro con ese título");
            return new ResponseEntity<>(datos, HttpStatus.CONFLICT);
        }

        if (libro.getId() != null) {
            // Si el libro tiene un ID, intenta actualizarlo
            return updateLibro(libro);
        } else {
            // Si no tiene un ID, guárdalo como un nuevo libro
            libroRepositorio.save(libro);
            datos.put("message", "Se guardó correctamente");
            datos.put("Datos", libro);
            return new ResponseEntity<>(datos, HttpStatus.CREATED);
        }
    }

    public ResponseEntity<Object> updateLibro(Libro libro) {
        Optional<Libro> libroExistente = libroRepositorio.findById(libro.getId());

        if (libroExistente.isPresent()) {
            libroRepositorio.save(libro);
            datos.put("message", "Se actualizó correctamente el libro");
            datos.put("Datos", libro);
            return new ResponseEntity<>(datos, HttpStatus.OK);
        } else {
            datos.put("Error!", true);
            datos.put("message", "No se encontró un libro con el ID proporcionado");
            return new ResponseEntity<>(datos, HttpStatus.NOT_FOUND);
        }
    }


    public ResponseEntity<Object> deleteLibro(Long id){
        datos = new HashMap<>();
        boolean existe=this.libroRepositorio.existsById(id);
        if(!existe){
            datos.put("Error!",true);
            datos.put("message","No existe un libro con ese id");
            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
        libroRepositorio.deleteById(id);
        datos.put("message","Libro Eliminado correctamente!");
        return new ResponseEntity<>(
                datos,
                HttpStatus.ACCEPTED
        );
    }

}