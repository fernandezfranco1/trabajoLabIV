package com.back.spring.usuario;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServicio {

    private final UsuarioRepositorio usuarioRepositorio;

    @Autowired
    public UsuarioServicio(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }
    public List<Usuario> getUsuarios() {
        return this.usuarioRepositorio.findAll();
    }

    public ResponseEntity<Object> nuevoUsuario(Usuario usuario) {
        Optional<Usuario> res = usuarioRepositorio.findUsuarioByNomusuario(usuario.getNomusuario());
        HashMap<String, Object> datos = new HashMap<>();

        if (res.isPresent() && usuario.getId() == null) {
            datos.put("Error!", true);
            datos.put("message", "Ya existe un usuario con ese nombre de usuario");
            return new ResponseEntity<>(datos, HttpStatus.CONFLICT);
        }

        datos.put("message", "Usuario registrado correctamente!");
        if (usuario.getId() != null) {
            datos.put("message", "Usuario actualizado correctamente!");
        }

        usuarioRepositorio.save(usuario);
        datos.put("Datos", usuario);
        return new ResponseEntity<>(datos, HttpStatus.CREATED);
    }

    public ResponseEntity<Object> actualizarUsuario(Usuario usuario) {
        HashMap<String, Object> datos = new HashMap<>();

        // Validación para verificar si el usuario existe
        if (usuario.getId() == null || !usuarioRepositorio.existsById(usuario.getId())) {
            datos.put("Error!", true);
            datos.put("message", "No se puede encontrar el usuario para actualizar");
            return new ResponseEntity<>(datos, HttpStatus.NOT_FOUND);
        }

        // Obtener el usuario existente de la base de datos
        Usuario usuarioExistente = usuarioRepositorio.findById(usuario.getId()).orElse(null);

        if (usuarioExistente != null) {
            // Actualizar las propiedades del usuario existente con los valores del nuevo usuario
            usuarioExistente.setNomusuario(usuario.getNomusuario());
            usuarioExistente.setContraseña(usuario.getContraseña());

            // Agrega aquí más propiedades que puedan necesitar actualización

            // Guardar el usuario actualizado en la base de datos
            usuarioRepositorio.save(usuarioExistente);

            datos.put("message", "Usuario actualizado correctamente!");
            datos.put("Datos", usuarioExistente);
            return new ResponseEntity<>(datos, HttpStatus.OK);
        } else {
            datos.put("Error!", true);
            datos.put("message", "No se puede encontrar el usuario para actualizar");
            return new ResponseEntity<>(datos, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Object> eliminarUsuario(Long id) {
        HashMap<String, Object> datos = new HashMap<>();
        boolean existe = this.usuarioRepositorio.existsById(id);

        if (!existe) {
            datos.put("Error!", true);
            datos.put("message", "No existe un usuario con ese id");
            return new ResponseEntity<>(datos, HttpStatus.CONFLICT);
        }

        usuarioRepositorio.deleteById(id);
        datos.put("message", "Usuario eliminado correctamente!");
        return new ResponseEntity<>(datos, HttpStatus.ACCEPTED);
    }
    public Optional<Usuario> findUsuarioByNomusuario(String nomusuario) {
        return usuarioRepositorio.findUsuarioByNomusuario(nomusuario);
    }
}