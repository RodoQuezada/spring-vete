package com.gazulabs.veterinaria.springboot.app.models.services;

import com.gazulabs.veterinaria.springboot.app.models.entity.Diagnostico;

import java.util.List;

public interface IDiagnosticoService {

    public void save(Diagnostico diagnostico);

    public Diagnostico findOneById(Long id);

    public void delete (Long id);

    public List<Diagnostico> findAll();


}
