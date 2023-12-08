package com.back.spring.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/usuarios")
public class ControladorAutenticacion {
    @Autowired
    private UsuarioServicio usuarioServicio;

    @PostMapping("/login")
    public ResponseEntity<Object> loginUsuario(@RequestBody Usuario usuario) {
        Optional<Usuario> usuarioRegistrado = usuarioServicio.findUsuarioByNomusuario(usuario.getNomusuario());

        if (usuarioRegistrado.isPresent() && usuarioRegistrado.get().getContraseña().equals(usuario.getContraseña())) {
            // Autenticación exitosa
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            // Autenticación fallida
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
