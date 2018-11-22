package com.gazulabs.veterinaria.springboot.app.models.services;

import com.gazulabs.veterinaria.springboot.app.models.entity.Especie;

import java.util.List;

public interface IEspecieService {

    public void save(Especie especie);

    public List<Especie> findAll();

    public void delete(Long id);

    public Especie findById(Long id);

}
