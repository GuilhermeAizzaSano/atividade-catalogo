package br.com.fatec.catalogo.config;

import br.com.fatec.catalogo.models.CategoriaModel;
import br.com.fatec.catalogo.models.UsuarioModel;
import br.com.fatec.catalogo.repositories.CategoriaRepository;
import br.com.fatec.catalogo.repositories.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initAdmin(UsuarioRepository repository,
                                       CategoriaRepository categoriaRepository,
                                       JdbcTemplate jdbcTemplate,
                                       PasswordEncoder encoder) {
        return args -> {
            if (repository.findByUsername("admin").isEmpty()) {
                UsuarioModel admin = new UsuarioModel();
                admin.setUsername("admin");
                admin.setPassword(encoder.encode("12345"));
                admin.setRole("ADMIN");
                repository.save(admin);
            }

            if (categoriaRepository.count() == 0) {
                CategoriaModel categoria1 = new CategoriaModel();
                categoria1.setNome("Informatica");

                CategoriaModel categoria2 = new CategoriaModel();
                categoria2.setNome("Escritorio");

                categoriaRepository.save(categoria1);
                categoriaRepository.save(categoria2);
            }

            Long categoriaPadraoId = categoriaRepository.findByNomeIgnoreCase("Informatica")
                    .map(CategoriaModel::getIdCategoria)
                    .orElseGet(() -> {
                        CategoriaModel categoriaPadrao = new CategoriaModel();
                        categoriaPadrao.setNome("Informatica");
                        return categoriaRepository.save(categoriaPadrao).getIdCategoria();
                    });

            Integer colunaExiste = jdbcTemplate.queryForObject(
                    """
                    select count(*)
                    from information_schema.columns
                    where table_name = 'tb_produto'
                      and column_name = 'id_categoria'
                    """,
                    Integer.class
            );

            if (colunaExiste != null && colunaExiste > 0) {
                jdbcTemplate.update(
                        "update tb_produto set id_categoria = ? where id_categoria is null",
                        categoriaPadraoId
                );
            }
        };
    }
}
