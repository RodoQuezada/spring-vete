package com.gazulabs.veterinaria.springboot.app.controllers;

import com.gazulabs.veterinaria.springboot.app.models.entity.TipoUsuario;
import com.gazulabs.veterinaria.springboot.app.models.entity.Usuario;
import com.gazulabs.veterinaria.springboot.app.models.services.ITipoUsuarioService;
import com.gazulabs.veterinaria.springboot.app.models.services.IUsuarioService;
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
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private ITipoUsuarioService tipoUsuarioService;

    private static final String TITULO_MANTENEDOR = "Agregar Usuario";

    @GetMapping("/form")
    public String crear(Model model){
        Usuario usuario = new Usuario();
        List<Usuario> lstUsuarios = new ArrayList<>();
        try {
             lstUsuarios = usuarioService.findAll();
        }catch (Exception e){
            System.out.println(e.toString());
        }
        List<TipoUsuario> lstTipoUsuario = tipoUsuarioService.findAll();
        model.addAttribute("lstUsuarios", lstUsuarios);
        model.addAttribute("lstTipoUsuario", lstTipoUsuario);
        model.addAttribute("usuario", usuario);
        model.addAttribute("titulo", TITULO_MANTENEDOR);
        return "usuario/form";
    }

    @PostMapping("/form")
    public String guardar(@Valid Usuario usuario, Model model, BindingResult result, RedirectAttributes flash,
                          SessionStatus status){
        if (result.hasErrors()){
            model.addAttribute("titulo", TITULO_MANTENEDOR);
        }
        usuarioService.save(usuario);
        status.setComplete();
        flash.addFlashAttribute("success", "Usuario agregado con éxito");
        return "redirect:form";
    }

    @RequestMapping("/eliminar/{id}")
    public String eliminar(@PathVariable (value = "id") Long id, RedirectAttributes flash){
        if (id > 0 ){
            usuarioService.delete(id);
            flash.addFlashAttribute("success", "Usuario eliminado con exito");
        }else{
            flash.addFlashAttribute("error", "Error !!! ");
        }
        return "redirect:/usuario/form";
    }



}
