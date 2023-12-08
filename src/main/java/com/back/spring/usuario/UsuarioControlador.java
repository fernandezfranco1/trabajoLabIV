package com.back.spring.usuario;
import java.util.List;
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

@RestController
@RequestMapping(path = "api/v1/usuarios")
public class UsuarioControlador {
    private final UsuarioServicio usuarioServicio;
    @Autowired
    public UsuarioControlador(UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }
    @GetMapping
    public List<Usuario> getUsuarios() {
        return this.usuarioServicio.getUsuarios();
    }

    @PostMapping
    public ResponseEntity<Object> registrarUsuario(@RequestBody Usuario usuario) {
        return this.usuarioServicio.nuevoUsuario(usuario);
    }

    @PutMapping
    public ResponseEntity<Object> actualizarUsuario(@RequestBody Usuario usuario){
        return this.usuarioServicio.nuevoUsuario(usuario);
    }

    @DeleteMapping(path = "{usuarioId}")
    public ResponseEntity<Object> eliminarUsuario(@PathVariable("usuarioId") Long id) {
        return this.usuarioServicio.eliminarUsuario(id);
    }
}