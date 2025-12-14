package com.chickenfriends.api.repository;

import com.chickenfriends.api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByCorreo(String correo);
    boolean existsByCodigo(String codigo);
    boolean existsByCedula(String cedula);
    Usuario findByCodigoAndContrasena(String codigo, String contrasena);
    Optional<Usuario> findByCodigoOrNombresYApellidos(String codigo, String nombresYApellidos);

}
