package com.gazulabs.veterinaria.springboot.app.models.dao;

import com.gazulabs.veterinaria.springboot.app.models.entity.Especie;
import org.springframework.data.repository.CrudRepository;

public interface IEspecieDao extends CrudRepository<Especie, Long> {

}
