package com.gazulabs.veterinaria.springboot.app.controllers;

import com.gazulabs.veterinaria.springboot.app.models.entity.*;
import com.gazulabs.veterinaria.springboot.app.models.services.*;
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
import java.util.ArrayList;
import java.util.Date;
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

    @Autowired
    private IAtencionService atencionService;

    @Autowired
    private IUsuarioService usuarioService;

    private static final String TITULO_MANTENEDOR = "Agregar Ficha Atencion";

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/form/{pacienteId}")
    public String crearNuevaFicha(Model model, @PathVariable(value = "pacienteId") Long pacienteId) {
        FichaAtencion fichaAtencion = new FichaAtencion();
        Paciente paciente = pacienteService.findById(pacienteId);
        fichaAtencion.setPaciente(paciente);
        logger.info("--- estado atención: " + fichaAtencion.getEstadoAtencion());
        model.addAttribute("fichaAtencion", fichaAtencion);
        model.addAttribute("paciente", paciente);
        model.addAttribute("titulo", TITULO_MANTENEDOR);
        return "ficha-atencion/form";
    }

    @PostMapping("/form")
    public String guardar(@Valid FichaAtencion fichaAtencion, Model model, BindingResult result, RedirectAttributes flash,
                          SessionStatus status) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", TITULO_MANTENEDOR);
            flash.addFlashAttribute("error", "Ocurrio un error en el sistema: " + result.toString());
            return "paciente/ver/" + fichaAtencion.getPaciente().getId();
        }
        if (fichaAtencion == null) {
            model.addAttribute("titulo", TITULO_MANTENEDOR);
            flash.addFlashAttribute("error", "Ocurrio un error en el sistema: Ficha atención igual a null");
            return "paciente/ver/" + fichaAtencion.getPaciente().getId();
        } else {
            fichaAtencionService.save(fichaAtencion);
        }
        status.setComplete();
        flash.addFlashAttribute("success", "Ficha de atención ingresada con exito");
        return "redirect:/ficha-atencion/lista-sala-espera";
    }

    @GetMapping("/ver/{id}")
    public String ver(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
        if (id < 0) {
            flash.addFlashAttribute("error", "Error al consultar ficha");
            return "/ficha-atencion/";
        }
        model.put("fichaAtencion",fichaAtencionService.findById(id) );
        model.put("titulo", TITULO_MANTENEDOR);
        return "ficha-atencion/ver";
    }

    @GetMapping("/lista-fichas-atencion")
    public String listaFichas(Model model, RedirectAttributes flash) {
        List<FichaAtencion> fichas = fichaAtencionService.findAll();
        if (fichas.isEmpty()) {
            flash.addFlashAttribute("error", "Error al consultar ficha");
            return "ficha-atencion/lista-fichas-atencion";
        }
        model.addAttribute("fichas", fichas);
        model.addAttribute("titulo", TITULO_MANTENEDOR);
        return "ficha-atencion/lista-fichas-atencion";
    }

    @GetMapping("/lista-sala-espera")
    public String buscarListaEspera(Model model, RedirectAttributes flash) {
        List<FichaAtencion> listaFichasAtencionEnEspera = new ArrayList<>();
        List<FichaAtencion> fichas = fichaAtencionService.findAll();
        for (FichaAtencion f : fichas) {
            if (f.getEstadoAtencion() == 'p') {
                listaFichasAtencionEnEspera.add(f);
            }
        }
        logger.info("---tamaño lista sala espera: " + listaFichasAtencionEnEspera.size());
        model.addAttribute("fichas", listaFichasAtencionEnEspera);
        model.addAttribute("titulo", "Fichas de atenciones en espera");
        return "ficha-atencion/lista-sala-espera";
    }

    @RequestMapping(value = "/atender/{id}")
    public String atenderFicha(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
        if (id < 0) {
            flash.addFlashAttribute("error", "Error al consultar ficha");
            return "/ficha-atencion/";
        }
        FichaAtencion fichaTemp = fichaAtencionService.findById(id);
        fichaTemp.setEstadoAtencion('a');
        fichaAtencionService.save(fichaTemp);
        return "redirect:/ficha-atencion/lista-sala-espera";
    }

    @GetMapping("/lista-pacientes-atendidos")
    public String listaPacientesEnSalaEspera(Model model, RedirectAttributes flash) {
        List<FichaAtencion> listaPacientesEnSalaEspera = new ArrayList<>();
        List<FichaAtencion> fichas = fichaAtencionService.findAll();
        for (FichaAtencion f : fichas) {
            if (f.getEstadoAtencion() == 'a') {
                listaPacientesEnSalaEspera.add(f);
            }
        }
        logger.info("---tamaño lista en sala: " + listaPacientesEnSalaEspera.size());
        model.addAttribute("fichas", listaPacientesEnSalaEspera);
        model.addAttribute("titulo", "Fichas en anteción");
        return "ficha-atencion/lista-pacientes-atendidos";
    }

    @GetMapping("/ver-diagnostico/{id}")
    public String verConDiagnostico(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
        if (id < 0) {
            flash.addFlashAttribute("error", "Error al consultar ficha");
            return "/ficha-atencion/";
        }
        model.put("fichaAtencion", fichaAtencionService.findById(id));
        model.put("titulo", TITULO_MANTENEDOR);
        return "ficha-atencion/ver-diagnostico";
    }

    @GetMapping("/form-diagnostico/{id}")
    public String creaFormularioDiagnostico(@PathVariable(value = "id") Long id, Model model) {
        FichaAtencion fichaAtencion = fichaAtencionService.findById(id);
        model.addAttribute("fichaAtencion", fichaAtencion);
        model.addAttribute("titulo", "Agregar diagnostico");
        return "ficha-atencion/form-diagnostico";
    }

    @PostMapping("/form-diagnostico")
    public String guardarFormularioDiagnostico(@Valid FichaAtencion fichaAtencion, Model model, BindingResult result, RedirectAttributes flash,
                                               SessionStatus status) {
        fichaAtencion.setEstadoAtencion('f');
        fichaAtencionService.save(fichaAtencion);
        status.setComplete();
        model.addAttribute("fichaAtencion", fichaAtencion);
        model.addAttribute("titulo", "Agregar diagnostico");
        flash.addFlashAttribute("success", "Ficha de atención ingresada con exito");
        return "redirect:/ficha-atencion/lista-pacientes-altas";
    }

    @GetMapping("/lista-pacientes-altas")
    public String listaPacientesEnAlta(Model model, RedirectAttributes flash) {
        List<FichaAtencion> listaPacientesEnAlta = new ArrayList<>();
        List<FichaAtencion> fichas = fichaAtencionService.findAll();
        for (FichaAtencion f : fichas) {
            if (f.getEstadoAtencion() == 'f') {
                listaPacientesEnAlta.add(f);
            }
        }
        logger.info("---tamaño lista en alta: " + listaPacientesEnAlta.size());
        model.addAttribute("fichas", listaPacientesEnAlta);
        model.addAttribute("titulo", "Fichas clientes en alta");
        return "ficha-atencion/lista-pacientes-atendidos";
    }


    @GetMapping("/form-completo/{id}")
    public String formDiagnosticoCompleto(@PathVariable(value = "id") Long id, Model model) {
        Paciente paciente = pacienteService.findById(id);
        List<Usuario> usuarios = usuarioService.findAll();
        FichaAtencion ficha = new FichaAtencion();
        Atencion atencion = new Atencion();
        ficha.setPaciente(paciente);
        ficha.setFechaAtencion(new Date());
        //ficha.getAtenciones().add(atencion);
        model.addAttribute("fichaAtencion", ficha);
        model.addAttribute("paciente", paciente);
        model.addAttribute("atencion", atencion);
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("titulo", TITULO_MANTENEDOR);
        return "ficha-atencion/form-completo";
    }

    @PostMapping("/form-completo")
    public String guardarDiagnosticoCompleto(@Valid FichaAtencion fichaAtencion, @Valid Atencion atencion, Model model, BindingResult result, RedirectAttributes flash,
                                             SessionStatus status){
        System.out.println("-- entra en el guardar diagnostico");
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Lista de Clientes");
            return "ficha-atencion/lista-pacientes-atendidos";
        }
        if (fichaAtencion != null){
            fichaAtencion.setEstadoAtencion('f');
            fichaAtencionService.save(fichaAtencion);
            if (atencion != null) {
                System.out.println("-- funciona");
                atencion.setComentario("Sin comentario.");
                atencion.setFichaAtencion(fichaAtencion);
                atencionService.save(atencion);
                status.setComplete();
                flash.addFlashAttribute("success", "Ficha de atención ingresada con exito");
            }
        } else{
            System.out.println("-- Cagaso");
        }
        return "ficha-atencion/lista-pacientes-altas";
    }

    @GetMapping("/dar-alta/{id}")
    public String darAlta(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
        if (id < 0) {
            flash.addFlashAttribute("error", "Error al consultar ficha");
            return "/ficha-atencion/";
        }
        FichaAtencion ficha = fichaAtencionService.findById(id);
        ficha.setEstadoAtencion('f');
        fichaAtencionService.save(ficha);
       // model.put("fichaAtencion", fichaAtencionService.findById(id));
        model.put("titulo", TITULO_MANTENEDOR);
      //  return "ficha-atencion/ver-diagnostico";
        return "ficha-atencion/lista-pacientes-altas";
    }


}
