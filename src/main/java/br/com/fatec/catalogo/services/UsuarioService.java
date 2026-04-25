package br.com.fatec.catalogo.services;

import br.com.fatec.catalogo.models.UsuarioModel;
import br.com.fatec.catalogo.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    public List<UsuarioModel> buscar(String username) {
        if (username != null && !username.isBlank()) {
            return repository.findByUsernameContainingIgnoreCase(username);
        }
        return repository.findAll();
    }

    public UsuarioModel buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
    }

    public void salvar(UsuarioModel usuario) {
        boolean isNovo = (usuario.getIdUsuario() == null);

        if (isNovo && repository.existsByUsername(usuario.getUsername())) {
            throw new IllegalArgumentException("Já existe um usuário com esse nome de usuário.");
        }

        if (!isNovo) {
            UsuarioModel existente = buscarPorId(usuario.getIdUsuario());

            if (!existente.getUsername().equalsIgnoreCase(usuario.getUsername())
                    && repository.existsByUsername(usuario.getUsername())) {
                throw new IllegalArgumentException("Já existe um usuário com esse nome de usuário.");
            }

            usuario.setDataCadastro(existente.getDataCadastro());

            if (usuario.getPassword() == null || usuario.getPassword().isBlank()) {
                // Mantém a senha atual já criptografada
                usuario.setPassword(existente.getPassword());
            } else {
                // Criptografa a nova senha
                usuario.setPassword(encoder.encode(usuario.getPassword()));
            }
        } else {
            // Criptografa a senha do novo usuário
            usuario.setPassword(encoder.encode(usuario.getPassword()));
        }

        repository.save(usuario);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }
}