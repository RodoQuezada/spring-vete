package com.gazulabs.veterinaria.springboot.app.controllers;

import com.gazulabs.veterinaria.springboot.app.models.entity.Atencion;
import com.gazulabs.veterinaria.springboot.app.models.entity.FichaAtencion;
import com.gazulabs.veterinaria.springboot.app.models.entity.Usuario;
import com.gazulabs.veterinaria.springboot.app.models.services.IAtencionService;
import com.gazulabs.veterinaria.springboot.app.models.services.IFichaAtencionService;
import com.gazulabs.veterinaria.springboot.app.models.services.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/atencion")
@SessionAttributes("atencion")
public class AtencionController {

    @Autowired
    private IAtencionService atencionService;

    @Autowired
    private IFichaAtencionService fichaAtencionService;

    @Autowired
    private IUsuarioService usuarioService;

    private static final String TITULO_MANTENEDOR = "Agregar Atención";

    @GetMapping("/form/{fichaId}")
    public String crear(@PathVariable (value = "fichaId") Long fichaId, Map<String, Object> model,
                        RedirectAttributes flash){
        FichaAtencion ficha = fichaAtencionService.findById(fichaId);
        List<Usuario> usuarios = usuarioService.findAll();
        Atencion atencion = new Atencion();
        atencion.setFichaAtencion(ficha);
        model.put("titulo", TITULO_MANTENEDOR);
        model.put("usuarios", usuarios);
        model.put("ficha",ficha);
        model.put("atencion", atencion);
        return "atencion/form";
    }

    @PostMapping("/form")
    public String guardar(@Valid Atencion atencion, BindingResult result, Model model, RedirectAttributes flash,
                          SessionStatus status){
        if (result.hasErrors()){
            flash.addFlashAttribute("error", "Error al guardar la atención");
            model.addAttribute("titulo", TITULO_MANTENEDOR);
            return "atencion/form";
        }
        if (atencion == null) {
            flash.addFlashAttribute("error", "Objeto null");
            model.addAttribute("titulo", TITULO_MANTENEDOR);
            return "atencion/form";
        }else{
            atencionService.save(atencion);
        }
        status.setComplete();
        flash.addFlashAttribute("success","Atención agregada con éxtio");
        model.addAttribute("titulo",TITULO_MANTENEDOR);
        return "redirect:/ficha-atencion/ver-diagnostico/" + atencion.getFichaAtencion().getId();
    }

    @GetMapping("/ver/{id}")
    public String ver(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash){
        Atencion atencion = atencionService.findOneById(id);
        model.put("atencion",atencion);
        model.put("titulo",TITULO_MANTENEDOR);
        return "atencion/ver";
    }



}
