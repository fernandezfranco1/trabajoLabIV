package com.back.spring.libro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/libros")
public class LibroControlador {

    //Conecto el servicio con el controlador
    private final LibroServicio libroServicio;

    @Autowired
    public LibroControlador(LibroServicio libroServicio){
        this.libroServicio = libroServicio;
    }

    @GetMapping
    public List<Libro> getLibros(){
        return this.libroServicio.getLibros();
    }


    @PostMapping
    public ResponseEntity<Object> registrarLibro(@RequestBody Libro libro){
        return this.libroServicio.newLibro(libro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> actualizarLibro(@RequestBody Libro libro){
        return this.libroServicio.newLibro(libro);
    }

    @DeleteMapping(path = "{libroId}")
    public ResponseEntity<Object> eliminarLibro(@PathVariable("libroId") Long id){
        return this.libroServicio.deleteLibro(id);
    }

}