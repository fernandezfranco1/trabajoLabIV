package com.back.spring.prestamo;
import com.back.spring.libro.Libro;
import com.back.spring.libro.LibroRepositorio;
import com.back.spring.usuario.Usuario;
import com.back.spring.usuario.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrestamoServicio {

    private final PrestamoRepositorio prestamoRepositorio;
    private final LibroRepositorio libroRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;

    @Autowired
    public PrestamoServicio(PrestamoRepositorio prestamoRepositorio, LibroRepositorio libroRepositorio, UsuarioRepositorio usuarioRepositorio) {
        this.prestamoRepositorio = prestamoRepositorio;
        this.libroRepositorio = libroRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
    }

    public List<Prestamo> getPrestamos() {
        return this.prestamoRepositorio.findAll();
    }

    public ResponseEntity<Object> realizarPrestamo(Long idLibro, Long idUsuario) {
        // Lógica para realizar un préstamo
        Optional<Libro> libroOptional = libroRepositorio.findById(idLibro);
        Optional<Usuario> usuarioOptional = usuarioRepositorio.findById(idUsuario);

        if (libroOptional.isPresent() && usuarioOptional.isPresent()) {
            Libro libro = libroOptional.get();
            Usuario usuario = usuarioOptional.get();

            if (libro.isDisponible()) {
                Prestamo prestamo = new Prestamo(libro, usuario);

                prestamoRepositorio.save(prestamo);

                libro.setDisponible(false);
                libroRepositorio.save(libro);

                return new ResponseEntity<>("Préstamo realizado con éxito.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("El libro no está disponible para préstamo.", HttpStatus.CONFLICT);
            }
        } else {
            return new ResponseEntity<>("Libro o usuario no encontrado.", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Object> editarPrestamo(Long idPrestamo, Long idLibro, Long idUsuario) {
        // Lógica para editar un préstamo
        Optional<Prestamo> prestamoOptional = prestamoRepositorio.findById(idPrestamo);

        if (prestamoOptional.isPresent()) {
            Prestamo prestamo = prestamoOptional.get();
            Optional<Libro> libroOptional = libroRepositorio.findById(idLibro);
            Optional<Usuario> usuarioOptional = usuarioRepositorio.findById(idUsuario);

            if (libroOptional.isPresent() && usuarioOptional.isPresent()) {
                Libro nuevoLibro = libroOptional.get();
                Usuario nuevoUsuario = usuarioOptional.get();

                prestamo.setLibro(nuevoLibro);
                prestamo.setUsuario(nuevoUsuario);

                prestamoRepositorio.save(prestamo);

                return new ResponseEntity<>("Préstamo editado con éxito.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Libro o usuario no encontrado.", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("Préstamo no encontrado.", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Object> eliminarPrestamo(Long idPrestamo) {
        // Lógica para eliminar un préstamo
        Optional<Prestamo> prestamoOptional = prestamoRepositorio.findById(idPrestamo);

        if (prestamoOptional.isPresent()) {
            Prestamo prestamo = prestamoOptional.get();
            Libro libro = prestamo.getLibro();

            prestamoRepositorio.delete(prestamo);

            libro.setDisponible(true);
            libroRepositorio.save(libro);

            return new ResponseEntity<>("Préstamo eliminado con éxito.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Préstamo no encontrado.", HttpStatus.NOT_FOUND);
        }
    }
}