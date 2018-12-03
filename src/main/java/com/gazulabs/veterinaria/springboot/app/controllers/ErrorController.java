package com.gazulabs.veterinaria.springboot.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {


    private static final String TITULO_MANTENEDOR = "Control de errores";

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model){

        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");

        model.addAttribute("statusCode",statusCode);
        model.addAttribute("exception",exception);
        model.addAttribute("titulo", TITULO_MANTENEDOR);
        return "error";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
