package com.gazulabs.veterinaria.springboot.app.models.services;

import com.gazulabs.veterinaria.springboot.app.models.entity.FichaAtencion;

import java.util.List;

public interface IFichaAtencionService {

    public void save(FichaAtencion fichaAtencion);

    public FichaAtencion findById(Long id);

    public void delete(Long id);

    public List<FichaAtencion> findAll();

    public List<FichaAtencion> buscarFichaPorEstadoAtencion(char estado);


}
