package com.gazulabs.veterinaria.springboot.app.models.services;

import com.gazulabs.veterinaria.springboot.app.models.entity.Raza;

import java.util.List;

public interface IRazaService {

   public void save(Raza raza);

   public void delete(Long id);

   public List<Raza> findAll();

   public Raza findById(Long id);

}
