package com.chickenfriends.api.controller;

import com.chickenfriends.api.model.Usuario;
import com.chickenfriends.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/")
    public String home() {
        return "API Usuarios activa!";
    }

    @GetMapping
    public List<Usuario> getUsuarios() {
        return usuarioRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);

        if (usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario) {
        boolean correoExiste = usuarioRepository.existsByCorreo(usuario.getCorreo());
        boolean codigoExiste = usuarioRepository.existsByCodigo(usuario.getCodigo());
        boolean cedulaExiste = usuarioRepository.existsByCedula(usuario.getCedula());
        if (correoExiste) {
            return ResponseEntity.badRequest().body("El correo ya está registrado");
        }
        if (codigoExiste) {
            return ResponseEntity.badRequest().body("El código ya está registrado");
        }
        if (cedulaExiste) {
            return ResponseEntity.badRequest().body("La cédula ya está registrada");
        }
        usuarioRepository.save(usuario);
        return ResponseEntity.ok("Usuario creado con éxito");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> datos) {

        String entrada = datos.get("entrada");
        String contrasena = datos.get("contrasena");
        Usuario encontrado = usuarioRepository
                .findByCodigoOrNombresYApellidos(entrada, entrada)
                .orElse(null);
        if (encontrado == null) {
            return ResponseEntity.status(401).body("Usuario no encontrado");
        }
        if (!encontrado.getContrasena().equals(contrasena)) {
            return ResponseEntity.status(401).body("Contraseña incorrecta");
        }
        return ResponseEntity.ok(encontrado);
    }

    @PutMapping("/{id}")
    public String actualizarUsuario(@PathVariable long id, @RequestBody Usuario usuario) {
        Usuario actualizado = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        actualizado.setNombresYApellidos(usuario.getNombresYApellidos());
        actualizado.setTelefono(usuario.getTelefono());
        actualizado.setCorreo(usuario.getCorreo());
        actualizado.setCodigo(usuario.getCodigo());
        actualizado.setCedula(usuario.getCedula());
        actualizado.setContrasena(usuario.getContrasena());
        usuarioRepository.save(actualizado);
        return "Usuario actualizado";
    }

    @DeleteMapping("/{id}")
    public String eliminarUsuario(@PathVariable long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuarioRepository.delete(usuario);
        return "Usuario eliminado";
    }
}
