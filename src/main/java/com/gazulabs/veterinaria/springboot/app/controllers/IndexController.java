package com.gazulabs.veterinaria.springboot.app.controllers;

import com.gazulabs.veterinaria.springboot.app.models.entity.FichaAtencion;
import com.gazulabs.veterinaria.springboot.app.models.services.IFichaAtencionService;
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
import java.util.Map;

@Controller
public class IndexController {

    @Autowired
    private IFichaAtencionService fichaAtencionService;

    @GetMapping("/")
    public String index(Model model){
        List<FichaAtencion> fichas = new ArrayList<>();
        for (FichaAtencion f: fichaAtencionService.findAll()) {
            if (f.getEstadoAtencion() == 'f' && f.getEstadoPago() == false){
                fichas.add(f);
            }
        }
        model.addAttribute("lstAtenciones", fichas);
        model.addAttribute("titulo", "Sistema Veterinaria");
        return "index";
    }


    @RequestMapping("/pagado/{id}")
    public String pagarFicha(@PathVariable(value = "id") Long id, RedirectAttributes flash, Map<String, Object> model){
        FichaAtencion fichaAtencion = null;
        if (id > 0 ){
            fichaAtencion = fichaAtencionService.findById(id);
            if (fichaAtencion == null){
                flash.addFlashAttribute("error", "El ID de la ficha de antencion no existe en la BBDD!");
                return "redirect:/index"; }
            }
        model.put("fichaAtencion",fichaAtencion);
        model.put("titulo", "Home Veterinaria");
        return "/pago";
    }

    @PostMapping("/pago")
    public String pagarFicha(@Valid FichaAtencion fichaAtencion, BindingResult result, Model model,
                             RedirectAttributes flash, SessionStatus status){
        if (result.hasErrors()){
            flash.addFlashAttribute("error", "Wrong !!! ");
            model.addAttribute("titulo", "Home Veterinaria");
            return "/";
        }
        fichaAtencion.setEstadoPago(true);
        fichaAtencionService.save(fichaAtencion);
        status.setComplete();
        flash.addFlashAttribute("success", "Se realizo pago del servicio");
        return "redirect:/";
    }

}
