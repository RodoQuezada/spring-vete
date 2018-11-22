package com.gazulabs.veterinaria.springboot.app.controllers;

import com.gazulabs.veterinaria.springboot.app.models.entity.Especie;
import com.gazulabs.veterinaria.springboot.app.models.services.IEspecieService;
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
@RequestMapping("/especie")
public class EspecieController {

    @Autowired
    private IEspecieService especieService;

    private static final String TITULO_MANTENEDOR = "Agregar especie";

    @GetMapping("/form")
    public String crear(Model model) {
        Especie especie = new Especie();
        List<Especie> lstEspecie = especieService.findAll();
        model.addAttribute("lstEspecies", lstEspecie);
        model.addAttribute("especie", especie);
        model.addAttribute("titulo", TITULO_MANTENEDOR);
        return "especie/form";
    }

    @PostMapping("/form")
    public String guardar(@Valid Especie especie, Model model, BindingResult result, RedirectAttributes flash,
                          SessionStatus status) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", TITULO_MANTENEDOR);
        }
        try {
            if (especie.getNombre().isEmpty() || especie.getNombre().length() < 4){
                flash.addFlashAttribute("error", "Nombre de especie no es valido");
                return "redirect:/especie/form";
            }else{
                especieService.save(especie);
            }
        } catch (Exception e) {
            System.out.println("--->" + e);
        }
        status.setComplete();
        flash.addFlashAttribute("success", "Especie creada con éxito");
        return "redirect:/especie/form";
    }

    @RequestMapping("/eliminar/{id}")
    public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
        if (id > 0) {
            especieService.delete(id);
            flash.addFlashAttribute("success", "Especie eliminada con exito");
        } else {
            flash.addFlashAttribute("error", "Error !!! ");
        }
        return "redirect:/especie/form";
    }

}
