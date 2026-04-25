package br.com.fatec.catalogo.controllers;

import br.com.fatec.catalogo.models.CategoriaModel;
import br.com.fatec.catalogo.services.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService service;

    @GetMapping
    public String listarCategorias(@RequestParam(required = false) String nome, Model model) {
        model.addAttribute("categorias", service.buscar(nome));
        model.addAttribute("termoBusca", nome);
        return "lista-categorias";
    }

    @GetMapping("/novo")
    public String exibirFormulario(Model model) {
        model.addAttribute("categoria", new CategoriaModel());
        return "cadastro-categoria";
    }

    @PostMapping("/novo")
    public String salvar(@Valid @ModelAttribute("categoria") CategoriaModel categoria, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "cadastro-categoria";
        }
        try {
            service.salvar(categoria);
            return "redirect:/categorias";
        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", e.getMessage());
            return "cadastro-categoria";
        }
    }

    @GetMapping("/editar/{id}")
    public String exibirEdicao(@PathVariable Long id, Model model) {
        model.addAttribute("categoria", service.buscarPorId(id));
        return "editar-categoria";
    }

    @PostMapping("/editar/{id}")
    public String atualizar(@PathVariable Long id, @Valid @ModelAttribute("categoria") CategoriaModel categoria, BindingResult result, Model model) {
        categoria.setIdCategoria(id);
        if (result.hasErrors()) {
            return "editar-categoria";
        }
        try {
            service.salvar(categoria);
            return "redirect:/categorias";
        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", e.getMessage());
            return "editar-categoria";
        }
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            service.excluir(id);
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/categorias";
    }
}
