package com.gazulabs.veterinaria.springboot.app.controllers;

import com.gazulabs.veterinaria.springboot.app.models.entity.Cliente;
import com.gazulabs.veterinaria.springboot.app.models.entity.FichaAtencion;
import com.gazulabs.veterinaria.springboot.app.models.entity.Paciente;
import com.gazulabs.veterinaria.springboot.app.models.services.IClienteService;
import com.gazulabs.veterinaria.springboot.app.models.services.IFichaAtencionService;
import com.gazulabs.veterinaria.springboot.app.models.services.IPacienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/ficha-atencion")
@SessionAttributes("fichaAtencion")
public class FichaAtencionController {

    @Autowired
    private IFichaAtencionService fichaAtencionService;

    @Autowired
    private IPacienteService pacienteService;

    private static final String TITULO_MANTENEDOR = "Agregar Ficha Atencion";

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/form/{pacienteId}")
    public String crearNuevaFicha(Model model, @PathVariable(value = "pacienteId") Long pacienteId){
        FichaAtencion fichaAtencion = new FichaAtencion();
        Paciente paciente = pacienteService.findById(pacienteId);
        fichaAtencion.setPaciente(paciente);
        model.addAttribute("fichaAtencion", fichaAtencion);
        model.addAttribute("paciente", paciente);
        model.addAttribute("titulo",TITULO_MANTENEDOR);
        return "ficha-atencion/form";
    }

    @PostMapping("/form")
    public String guardar(@Valid FichaAtencion fichaAtencion, Model model, BindingResult result, RedirectAttributes flash,
                          SessionStatus status){
        if (result.hasErrors()){
            model.addAttribute("titulo",TITULO_MANTENEDOR);
            flash.addFlashAttribute("error", "Ocurrio un error en el sistema: "+ result.toString());
            return "paciente/ver/"+ fichaAtencion.getPaciente().getId();
        }
        if (fichaAtencion == null){
            model.addAttribute("titulo",TITULO_MANTENEDOR);
            flash.addFlashAttribute("error", "Ocurrio un error en el sistema: Ficha atención igual a null");
            return "paciente/ver/"+ fichaAtencion.getPaciente().getId();
        }else{
            fichaAtencionService.save(fichaAtencion);
        }
        status.setComplete();
        flash.addFlashAttribute("success", "Ficha de atención ingresada con exito");
        return "redirect:/ficha-atencion/ver/" + fichaAtencion.getId();
    }

    @GetMapping("/ver/{id}")
    public String ver(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash){
        if (id<0){
            flash.addFlashAttribute("error","Error al consultar ficha");
            return "/ficha-atencion/";
        }
        model.put("fichaAtencion", fichaAtencionService.findById(id));
        model.put("titulo", TITULO_MANTENEDOR);
        return "ficha-atencion/ver";
    }

    @GetMapping("lista-fichas-atencion")
    public String listaFichas(Model model, RedirectAttributes flash){
        List<FichaAtencion> fichas = fichaAtencionService.findAll();
        if (fichas.isEmpty()){
            flash.addFlashAttribute("error","Error al consultar ficha");
            return "ficha-atencion/lista-fichas-atencion";
        }
        model.addAttribute("fichas",fichas);
        model.addAttribute("titulo",TITULO_MANTENEDOR);
        return "ficha-atencion/lista-fichas-atencion";
    }

}
