package com.gazulabs.veterinaria.springboot.app.controllers;


import com.gazulabs.veterinaria.springboot.app.models.entity.Cliente;
import com.gazulabs.veterinaria.springboot.app.models.entity.Paciente;
import com.gazulabs.veterinaria.springboot.app.models.services.IClienteService;
import com.gazulabs.veterinaria.springboot.app.models.services.IPacienteService;
import com.gazulabs.veterinaria.springboot.app.models.services.IUploadFileService;
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
import java.util.Map;


@Controller
@RequestMapping("/paciente")
@SessionAttributes("paciente")
public class PacienteController {

    @Autowired
    private IClienteService clienteService;

    @Autowired
    private IPacienteService pacienteService;

    @Autowired
    private IUploadFileService uploadFileService;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping(value = "/uploads/{filename:.+}")
    public ResponseEntity<Resource> verFoto(@PathVariable String filename) throws MalformedURLException {
        Resource recurso = null;
        log.info("entra a controlador verFoto");

            recurso = uploadFileService.load(filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
                .body(recurso);
    }

    @GetMapping("/form/{clienteId}")
    public String crear(@PathVariable(value = "clienteId") Long clienteId, Map<String, Object> model,
                        RedirectAttributes flash){
        Cliente cliente = clienteService.findOneById(clienteId);
        if (cliente.getId() == null || cliente.getId() < 1){
            flash.addFlashAttribute("error", "El cliente no existe en la BBDD");
            return "redirect:/cliente/listar";
        }
        Paciente paciente = new Paciente();
        paciente.setCliente(cliente);
        model.put("paciente", paciente);
        model.put("titulo","Agregar Paciente");
        return "paciente/form";
    }

    @PostMapping("/form")
    public String guardar(@RequestParam("file") MultipartFile foto, @Valid Paciente paciente, Model model, BindingResult result, RedirectAttributes flash, SessionStatus status) throws IOException {
        if (result.hasErrors()){
            model.addAttribute("titulo","Agregar paciente");
            return "paciente/form";
        }
        if (!foto.isEmpty()){
            String uniqueFilename = null;
            uniqueFilename = uploadFileService.copy(foto);
            flash.addFlashAttribute("info","Has subido correctamente '" + uniqueFilename + "'");
            paciente.setFoto(uniqueFilename);
        }
        pacienteService.save(paciente);
        status.setComplete();
        flash.addFlashAttribute("success", "Paciente agregado con éxito");
        return "redirect:/cliente/ver/" + paciente.getCliente().getId();
    }

    @GetMapping("/ver/{id}")
    public String ver(@PathVariable (value = "id")Long id,Model model, RedirectAttributes flash){
            Paciente paciente = pacienteService.findById(id);
            if (paciente == null){
                flash.addFlashAttribute("error","Paciente no existe en la BBDD");
                return "redirect:/cliente/listar";
            }
        model.addAttribute("paciente",paciente);
        model.addAttribute("titulo", "Paciente: ".concat(paciente.getNombre()));
        return "paciente/ver";
    }


}
