package br.com.fatec.catalogo.controllers;

import br.com.fatec.catalogo.models.UsuarioModel;
import br.com.fatec.catalogo.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @GetMapping
    public String listarUsuarios(@RequestParam(required = false) String username, Model model) {
        model.addAttribute("usuarios", service.buscar(username));
        model.addAttribute("termoBusca", username);
        return "lista-usuarios";
    }

    @GetMapping("/novo")
    public String exibirFormulario(Model model) {
        model.addAttribute("usuario", new UsuarioModel());
        return "cadastro-usuario";
    }

    @PostMapping("/novo")
    public String salvarUsuario(@Valid @ModelAttribute("usuario") UsuarioModel usuario,
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "cadastro-usuario";
        }
        try {
            service.salvar(usuario);
            return "redirect:/usuarios";
        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", e.getMessage());
            return "cadastro-usuario";
        }
    }

    @GetMapping("/editar/{id}")
    public String exibirEdicao(@PathVariable Long id, Model model) {
        model.addAttribute("usuario", service.buscarPorId(id));
        return "editar-usuario";
    }

    @PostMapping("/editar/{id}")
    public String atualizarUsuario(@PathVariable Long id,
                                   @Valid @ModelAttribute("usuario") UsuarioModel usuario,
                                   BindingResult result, Model model) {
        usuario.setIdUsuario(id);
        if (result.hasErrors()) {
            return "editar-usuario";
        }
        try {
            service.salvar(usuario);
            return "redirect:/usuarios";
        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", e.getMessage());
            return "editar-usuario";
        }
    }

    @GetMapping("/excluir/{id}")
    public String excluirUsuario(@PathVariable Long id) {
        service.excluir(id);
        return "redirect:/usuarios";
    }
}
