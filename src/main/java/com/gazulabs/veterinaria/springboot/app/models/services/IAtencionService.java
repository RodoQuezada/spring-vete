package com.gazulabs.veterinaria.springboot.app.models.services;

import com.gazulabs.veterinaria.springboot.app.models.entity.Atencion;
import com.gazulabs.veterinaria.springboot.app.models.services.Impl.AtencionServiceImpl;

import java.util.List;

public interface IAtencionService  {

    public void save(Atencion atencion);

    public Atencion findOneById(Long id);

    public void delete (Long id);

    public List<Atencion> findAll();


}
