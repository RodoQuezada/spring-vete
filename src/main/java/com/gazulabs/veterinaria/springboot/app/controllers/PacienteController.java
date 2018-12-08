package com.gazulabs.veterinaria.springboot.app.controllers;


import com.gazulabs.veterinaria.springboot.app.models.entity.Cliente;
import com.gazulabs.veterinaria.springboot.app.models.entity.Especie;
import com.gazulabs.veterinaria.springboot.app.models.entity.Paciente;
import com.gazulabs.veterinaria.springboot.app.models.entity.Raza;
import com.gazulabs.veterinaria.springboot.app.models.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/paciente")
@SessionAttributes("paciente")
public class PacienteController {

    @Autowired
    private IClienteService clienteService;

    @Autowired
    private IPacienteService pacienteService;

    @Autowired
    private IEspecieService especieService;

    @Autowired
    private IRazaService razaService;

    @Autowired
    private IUploadFileService uploadFileService;

    private Long idClienteTemporal;

    private  Long idPacienteTemporal;

    private  Long idRazaTemporal;

    private  Long idEspecieTemporal;

    private static List<Raza> listaRazas = new ArrayList<Raza>();

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final String TITULO_MANTENEDOR = "Agregar Paciente";


    @GetMapping(value = "/uploads/{filename:.+}")
    public ResponseEntity<Resource> verFoto(@PathVariable String filename) throws MalformedURLException {
        Resource recurso = null;
        logger.info("entra a controlador verFoto");

        recurso = uploadFileService.load(filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
                .body(recurso);
    }

    @GetMapping("/form/{clienteId}")
    public String crear(@PathVariable(value = "clienteId") Long clienteId, Map<String, Object> model,
                        RedirectAttributes flash) {
        Cliente cliente = clienteService.findOneById(clienteId);
        if (cliente.getId() == null || cliente.getId() < 1) {
            flash.addFlashAttribute("error", "El cliente no existe en la BBDD");
            return "redirect:/cliente/listar";
        }
        Paciente paciente = new Paciente();
        paciente.setCliente(cliente);
        List<Especie> lstEspecies = especieService.findAll();

        Collections.sort(lstEspecies, new Comparator<Especie>() {
            @Override
            public int compare(Especie o1, Especie o2) {
                return o1.getNombre().compareTo(o2.getNombre());
            }
        });

        idClienteTemporal = cliente.getId();
        model.put("paciente", paciente);
        model.put("titulo", TITULO_MANTENEDOR);
        model.put("lstEspecies", lstEspecies);
        model.put("listaRazas", listaRazas);
        return "paciente/form";
    }

    @PostMapping("/form")
    public String guardar(@RequestParam("file") MultipartFile foto, @Valid Paciente paciente, Model model, BindingResult result, RedirectAttributes flash, SessionStatus status) throws IOException {

        java.util.Date fecha = new Date();
        if (result.hasErrors()) {
            flash.addFlashAttribute("error", "Error");
            model.addAttribute("titulo", "Agregar paciente");
            return "paciente/form";
        }
        if (!foto.isEmpty()) {
            String uniqueFilename = null;
            uniqueFilename = uploadFileService.copy(foto);
            flash.addFlashAttribute("info", "Has subido correctamente '" + uniqueFilename + "'");
            paciente.setFoto(uniqueFilename);
        }


        //SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");


        if (paciente.getFechaFallecimiento() != null){
            if (paciente.getFechaNacimiento().before(paciente.getFechaFallecimiento())){
                logger.info("la fecha de nacimiento es anterior a la fecha de fallecimiento ");
                pacienteService.save(paciente);
                status.setComplete();
                flash.addFlashAttribute("success", "Paciente agregado con éxito");
                return "redirect:/cliente/ver/" + paciente.getCliente().getId();
            }else {
                logger.info("la fecha de fallecimiento es anterior a la fecha de nacimiento ... o sea esta mal la wea");
                flash.addFlashAttribute("error", "Error: la fecha de fallecimiento no puede ser posterior a la de nacimiento");
                model.addAttribute("titulo", "Agregar paciente");
                return "redirect:/cliente/ver/" + paciente.getCliente().getId();
            }
        }else {

            String mensajeFlash = (paciente.getId() != null) ? "Paciente editado con éxito" : "Paciente creado con éxito";

            pacienteService.save(paciente);
            status.setComplete();
            flash.addFlashAttribute("success", mensajeFlash);
            return "redirect:/cliente/ver/" + paciente.getCliente().getId();
        }
    }

    @GetMapping("/ver/{id}")
    public String ver(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
        Paciente paciente = pacienteService.findById(id);
        if (paciente == null) {
            flash.addFlashAttribute("error", "Paciente no existe en la BBDD");
            return "redirect:/cliente/listar";
        }
        model.addAttribute("paciente", paciente);
        model.addAttribute("titulo", "Paciente: ".concat(paciente.getNombre()));
        return "paciente/ver";
    }

    @RequestMapping("/editar/{id}")
    public String editar (@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash){
        Paciente paciente = null;
        if (id > 0){
            paciente = pacienteService.findById(id);
            if (paciente == null){
                flash.addFlashAttribute("error", "El paciente no es encontrado");
                return "cliente/ver/" + paciente.getCliente().getId();
            }
        }
        logger.info("--Clase Editar --  Raza : " .concat(paciente.getRaza().getNombre()));

        List<Especie> lstEspecies = especieService.findAll();

        Collections.sort(lstEspecies, new Comparator<Especie>() {
            @Override
            public int compare(Especie o1, Especie o2) {
                return o1.getNombre().compareTo(o2.getNombre());
            }
        });
        logger.info("id --- > " + id);
        logger.info("id statico request edit: " + idPacienteTemporal);
        idPacienteTemporal = id;
        logger.info("id statico request 22222222222: " + idPacienteTemporal);
        model.put("lstEspecies", lstEspecies);
        model.put("listaRazas", listaRazas);
        model.put("paciente", paciente);
        model.put("titulo", "Editar Paciente");
     //   return "paciente/form";
        return  "paciente/editar";
    }

    //implementación del dropdownlist en cascada.
    @GetMapping("/agregar-raza")
    public @ResponseBody
    List<Raza> findAllRaza(@RequestParam(value = "especieId") Long especieId) {
        Especie especie = especieService.findById(especieId);
        List<Raza> lstRazaFiltradaPorEspecie = new ArrayList<>();
        for (Raza r : razaService.findAll()) {
            if (especie.getId() == r.getEspecie().getId()) {
                lstRazaFiltradaPorEspecie.add(r);
            }
        }
        return lstRazaFiltradaPorEspecie;
    }

    public List<Raza> listadeRazas(@PathVariable(value = "especieId") Long especieId) {
        Especie especie = especieService.findById(especieId);
        List<Raza> lstRazaFiltradaPorEspecie = new ArrayList<>();
        for (Raza r : razaService.findAll()) {
            if (especie.getId() == r.getEspecie().getId()) {
                lstRazaFiltradaPorEspecie.add(r);
            }
        }
        return lstRazaFiltradaPorEspecie;
    }


    //Esto se puede mejorar con @Query para hacer solo una llamada.
    @GetMapping("/cargarlistas/{id}")
    public String cargarListaRazas(@PathVariable(value = "id") Long id, Model model) {
        logger.info("-- Entra a cargar lista de raza ");
        List<Raza> lstRaza = razaService.findAll();
        listaRazas.clear();
        for (Raza r : lstRaza) {
            if (r.getEspecie().getId() == id) {
                listaRazas.add(r);
            }
        }
        model.addAttribute("listaRazas", listaRazas);
        return "redirect:/paciente/form/ajax/";
    }

    @GetMapping("/form/ajax/")
    public String experimento(Map<String, Object> model,
                              RedirectAttributes flash) {
        logger.info("-- Cambiar a experimento");
        Cliente cliente = clienteService.findOneById(idClienteTemporal);
        if (cliente == null) {
            flash.addFlashAttribute("Error", "El cliente no existe en la BBDD");
            return "redirect:/listar";
        }
        Paciente paciente = new Paciente();
        paciente.setCliente(cliente);
        List<Especie> lstEspecies = especieService.findAll();

        Collections.sort(lstEspecies, new Comparator<Especie>() {
            @Override
            public int compare(Especie o1, Especie o2) {
                return o1.getNombre().compareTo(o2.getNombre());
            }
        });

        Collections.sort(listaRazas, new Comparator<Raza>() {
            @Override
            public int compare(Raza o1, Raza o2) {
                return o1.getNombre().compareTo(o2.getNombre());
            }
        });


        model.put("listaRazas", listaRazas);
        model.put("lstEspecies", lstEspecies);
      //  model.put("paciente", paciente);
        model.put("titulo", TITULO_MANTENEDOR);
        return "paciente/form::listaDeRazasFiltrada";
    }

    @RequestMapping(value = "/eliminar/{id}")
    public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash){
        Paciente paciente = pacienteService.findById(id);
        if (id > 0){
         pacienteService.delete(id);
          flash.addFlashAttribute("success", "Paciente eliminado con éxito");
        }
        return "redirect:/cliente/ver/" + paciente.getCliente().getId();
    }




}
