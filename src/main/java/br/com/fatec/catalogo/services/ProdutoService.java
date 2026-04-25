package br.com.fatec.catalogo.services;

import br.com.fatec.catalogo.models.ProdutoModel;
import br.com.fatec.catalogo.repositories.CategoriaRepository;
import br.com.fatec.catalogo.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<ProdutoModel> listarTodos() {
        return repository.findAll();
    }

    public List<ProdutoModel> buscar(String nome, Long categoriaId) {
        String nomeNormalizado = (nome == null || nome.trim().isEmpty()) ? null : nome.trim();

        if (nomeNormalizado == null && categoriaId == null) {
            return repository.findAll();
        }
        if (nomeNormalizado == null) {
            return repository.findByCategoria_IdCategoria(categoriaId);
        }
        if (categoriaId == null) {
            return repository.findByNomeContainingIgnoreCase(nomeNormalizado);
        }
        return repository.findByNomeContainingIgnoreCaseAndCategoria_IdCategoria(nomeNormalizado, categoriaId);
    }

    public ProdutoModel buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto nao encontrado"));
    }

    public void salvar(ProdutoModel produto) {
        boolean isNovo = produto.getIdProduto() == null;

        if (isNovo && repository.existsByNome(produto.getNome())) {
            throw new IllegalArgumentException("Ja existe um produto com o nome \"" + produto.getNome() + "\"");
        }

        if (produto.getCategoria() == null || produto.getCategoria().getIdCategoria() == null) {
            throw new IllegalArgumentException("Selecione uma categoria valida.");
        }

        var categoria = categoriaRepository.findById(produto.getCategoria().getIdCategoria())
                .orElseThrow(() -> new IllegalArgumentException("Categoria nao encontrada."));
        produto.setCategoria(categoria);

        repository.save(produto);
    }

    public void excluir(Long id) {
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Nao e possivel excluir produto vinculado a uma venda.");
        }
    }
}
