package com.gazulabs.veterinaria.springboot.app.controllers;

import com.gazulabs.veterinaria.springboot.app.models.entity.Diagnostico;
import com.gazulabs.veterinaria.springboot.app.models.entity.FichaAtencion;
import com.gazulabs.veterinaria.springboot.app.models.entity.Usuario;
import com.gazulabs.veterinaria.springboot.app.models.services.IAtencionService;
import com.gazulabs.veterinaria.springboot.app.models.services.IDiagnosticoService;
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
@RequestMapping("/diagnostico")
@SessionAttributes("diagnostico")
public class DiagnosticoController {


    @Autowired
    private IDiagnosticoService diagnosticoService;

    @Autowired
    private IFichaAtencionService fichaAtencionService;

    @Autowired
    private IUsuarioService usuarioService;

    private static final String TITULO_MANTENEDOR = "Agregar Diagnostico";


    @GetMapping("/form/{fichaId}")
    public String crear(@PathVariable (value = "fichaId") Long fichaId, Map<String, Object> model,
                        RedirectAttributes flash){
        FichaAtencion ficha = fichaAtencionService.findById(fichaId);
        List<Usuario> usuarios = usuarioService.findAll();
        Diagnostico diagnostico = new Diagnostico();
        diagnostico.setFichaAtencion(ficha);
        model.put("usuarios", usuarios);
        model.put("diagnostico", diagnostico);
        model.put("ficha",ficha);
        model.put("titulo", TITULO_MANTENEDOR);
        return "diagnostico/form";
    }

    @PostMapping("/form")
    public String guardar(@Valid Diagnostico diagnostico, BindingResult result, Model model, RedirectAttributes flash,
                          SessionStatus status ){
        if (result.hasErrors()){
            flash.addFlashAttribute("error", "Error al guardar el diagnostico");
            model.addAttribute("titulo", TITULO_MANTENEDOR);
            return "diagnostico/form";
        }

        if (diagnostico == null) {
            flash.addFlashAttribute("error", "Objeto null");
            model.addAttribute("titulo", TITULO_MANTENEDOR);
            return "diagnostico/form";
        }else{
            diagnosticoService.save(diagnostico);
        }
        status.setComplete();
        flash.addFlashAttribute("success","Diagnostico agregado con éxtio");
        model.addAttribute("titulo",TITULO_MANTENEDOR);
        return "redirect:/ficha-atencion/ver-diagnostico/" + diagnostico.getFichaAtencion().getId();
    }


    @GetMapping("/ver/{id}")
    public String ver(@PathVariable (value = "id") Long id, Map<String, Object> model, RedirectAttributes flash){
        Diagnostico diagnostico = diagnosticoService.findOneById(id);
        model.put("diagnostico", diagnostico);
        model.put("titulo", TITULO_MANTENEDOR);
        return "diagnostico/ver";
    }


}
