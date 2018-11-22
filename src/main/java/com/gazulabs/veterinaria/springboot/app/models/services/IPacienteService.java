package com.gazulabs.veterinaria.springboot.app.models.services;

import com.gazulabs.veterinaria.springboot.app.models.entity.Paciente;

import java.util.List;

public interface IPacienteService {

    public void save(Paciente paciente);

    public Paciente findById(Long id);

    public void delete(Long id);

    public List<Paciente> findAll();

    public List<Paciente> findAllPacienteByRutCliente(int rut);

}
