package com.gazulabs.veterinaria.springboot.app.controllers;


import com.gazulabs.veterinaria.springboot.app.models.entity.Especie;
import com.gazulabs.veterinaria.springboot.app.models.entity.Raza;
import com.gazulabs.veterinaria.springboot.app.models.services.IEspecieService;
import com.gazulabs.veterinaria.springboot.app.models.services.IRazaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/raza")
public class RazaController {

    @Autowired
    private IRazaService razaService;

    @Autowired
    private IEspecieService especieService;

    private static final String TITULO_MANTENEDOR = "Agregar Raza";

    @GetMapping("/form")
    public String crear(Model model) {
        Raza raza = new Raza();
        List<Raza> lstRazas = razaService.findAll();
        List<Especie> lstEspecies = especieService.findAll();
        model.addAttribute("lstRazas", lstRazas);
        model.addAttribute("lstEspecies", lstEspecies);
        model.addAttribute("raza", raza);
        model.addAttribute("titulo", TITULO_MANTENEDOR);
        return "raza/form";
    }

    @PostMapping("/form")
    public String guardar(@Valid Raza raza, Model model, BindingResult result, RedirectAttributes flash, SessionStatus status) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", TITULO_MANTENEDOR);
        }
        try {

            if (raza.getNombre().isEmpty() || raza.getNombre().length() < 2) {
                flash.addFlashAttribute("error", "Nombre de raza no es valido");
                return "redirect:/raza/form";
            } else if (raza.getEspecie() == null){
                flash.addFlashAttribute("error", "Debe agregar una especie");
                return "redirect:/raza/form";
            }else{
                razaService.save(raza);
            }
        } catch (Exception e) {
            System.out.println("error raza --->" + e);
        }
        status.setComplete();
        flash.addFlashAttribute("success", "Especie creada con éxito");
        return "redirect:/raza/form";
    }

    @RequestMapping("/eliminar/{id}")
    public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash){
        if (id > 0){
            razaService.delete(id);
            flash.addFlashAttribute("success", "Raza eliminada con exito");
        }else{
            flash.addFlashAttribute("error", "Error!!! ");
        }
        return "redirect:/raza/form";
    }




    /*
    @GetMapping("/ver/{id}")
    public String ver(@PathVariable (value = "id") Long id, Map<String, Object> model, RedirectAttributes flash ){
        Raza raza = razaService.findById(id);
        if (raza == null){
            flash.addFlashAttribute("error", "La raza no existe en la base de datos");
            return "redirect:/raza/form";
        }
        return "raza/ver/";
    }*/


}
