package com.gazulabs.veterinaria.springboot.app.controllers;

import com.gazulabs.veterinaria.springboot.app.models.entity.TipoUsuario;
import com.gazulabs.veterinaria.springboot.app.models.services.ITipoUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/tipo-usuario")
public class TipoUsuarioController {

    @Autowired
    private ITipoUsuarioService tipoUsuarioService;

    private static final String TITULO_MANTENEDOR = "Agregar Tipo Usuario";

    @GetMapping("/form")
    public String crear(Model model) {
        TipoUsuario tipoUsuario = new TipoUsuario();
        List<TipoUsuario> lstTipoUsuario = tipoUsuarioService.findAll();
        model.addAttribute("lstTipoUsuario", lstTipoUsuario);
        model.addAttribute("tipoUsuario", tipoUsuario);
        model.addAttribute("titulo", TITULO_MANTENEDOR);
        return "tipo-usuario/form";
    }

    @PostMapping("/form")
    public String guardar(@Valid TipoUsuario tipoUsuario, Model model, BindingResult result, RedirectAttributes flash,
                          SessionStatus status) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", TITULO_MANTENEDOR);
        }
        tipoUsuarioService.save(tipoUsuario);
        status.setComplete();
        flash.addFlashAttribute("success", "Tipo de usuario ingresado con extio");
        return "redirect:form";
    }

    @RequestMapping("/eliminar/{id}")
    public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
        if (id > 0) {
            tipoUsuarioService.delete(id);
            flash.addFlashAttribute("success", "Eliminado con éxito");
        } else {
            flash.addFlashAttribute("error", "Erorr !!!");
        }
        return "redirect:/tipo-usuario/form";
    }


}
