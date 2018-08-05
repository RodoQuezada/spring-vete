package com.gazulabs.veterinaria.springboot.app.models.dao;

import com.gazulabs.veterinaria.springboot.app.models.entity.Atencion;
import org.springframework.data.repository.CrudRepository;

public interface IAtencionDao extends CrudRepository<Atencion, Long> {

}
