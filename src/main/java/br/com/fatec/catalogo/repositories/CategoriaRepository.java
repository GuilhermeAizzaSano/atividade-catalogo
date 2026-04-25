package br.com.fatec.catalogo.repositories;

import br.com.fatec.catalogo.models.CategoriaModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<CategoriaModel, Long> {
    List<CategoriaModel> findByNomeContainingIgnoreCase(String nome);
    boolean existsByNome(String nome);
    Optional<CategoriaModel> findByNomeIgnoreCase(String nome);
}
