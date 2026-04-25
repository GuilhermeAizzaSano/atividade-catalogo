package br.com.fatec.catalogo.repositories;

import br.com.fatec.catalogo.models.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {
    Optional<UsuarioModel> findByUsername(String username);
    boolean existsByUsername(String username);
    java.util.List<UsuarioModel> findByUsernameContainingIgnoreCase(String username);
}