package br.com.fatec.catalogo.controllers;

import br.com.fatec.catalogo.models.CategoriaModel;
import br.com.fatec.catalogo.models.ProdutoModel;
import br.com.fatec.catalogo.services.CategoriaService;
import br.com.fatec.catalogo.services.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @Autowired
    private CategoriaService categoriaService;

    @ModelAttribute("categorias")
    public List<CategoriaModel> categorias() {
        return categoriaService.listarTodas();
    }

    @GetMapping
    public String listarProdutos(@RequestParam(required = false) String nome,
                                 @RequestParam(required = false) Long categoriaId,
                                 Model model) {
        model.addAttribute("produtos", service.buscar(nome, categoriaId));
        model.addAttribute("termoBusca", nome);
        model.addAttribute("categoriaSelecionada", categoriaId);
        return "lista-produtos";
    }

    @GetMapping("/novo")
    public String exibirFormulario(Model model) {
        model.addAttribute("produto", new ProdutoModel());
        return "cadastro-produto";
    }

    @PostMapping("/novo")
    public String salvarProduto(@Valid @ModelAttribute("produto") ProdutoModel produto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "cadastro-produto";
        }
        try {
            service.salvar(produto);
            return "redirect:/produtos";
        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", e.getMessage());
            return "cadastro-produto";
        }
    }

    @GetMapping("/editar/{id}")
    public String exibirEdicao(@PathVariable Long id, Model model) {
        model.addAttribute("produto", service.buscarPorId(id));
        return "editar-produto";
    }

    @PostMapping("/editar/{id}")
    public String atualizarProduto(@PathVariable Long id, @Valid @ModelAttribute("produto") ProdutoModel produto, BindingResult result, Model model) {
        produto.setIdProduto(id);
        if (result.hasErrors()) {
            return "editar-produto";
        }
        try {
            service.salvar(produto);
            return "redirect:/produtos";
        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", e.getMessage());
            return "editar-produto";
        }
    }

    @GetMapping("/excluir/{id}")
    public String excluirProduto(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            service.excluir(id);
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/produtos";
    }
}
