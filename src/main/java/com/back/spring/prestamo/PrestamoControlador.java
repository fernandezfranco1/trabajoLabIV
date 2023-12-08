package com.back.spring.prestamo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api/v1/prestamos")
public class PrestamoControlador {

    private final PrestamoServicio prestamoServicio;

    @Autowired
    public PrestamoControlador(PrestamoServicio prestamoServicio) {
        this.prestamoServicio = prestamoServicio;
    }

    @GetMapping
    public List<Prestamo> getPrestamos() {
        return this.prestamoServicio.getPrestamos();
    }

    @PostMapping
    public ResponseEntity<Object> realizarPrestamo(@RequestBody PrestamoRequest prestamoRequest) {
        return prestamoServicio.realizarPrestamo(prestamoRequest.getIdLibro(), prestamoRequest.getIdUsuario());
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<Object> editarPrestamo(@PathVariable Long id, @RequestBody Map<String, Long> requestBody) {
        Long idLibro = requestBody.get("idLibro");
        Long idUsuario = requestBody.get("idUsuario");

        return prestamoServicio.editarPrestamo(id, idLibro, idUsuario);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Object> eliminarPrestamo(@PathVariable Long id) {
        return prestamoServicio.eliminarPrestamo(id);
    }
}