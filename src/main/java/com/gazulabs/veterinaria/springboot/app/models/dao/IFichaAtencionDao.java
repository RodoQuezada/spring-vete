package com.gazulabs.veterinaria.springboot.app.models.dao;

import com.gazulabs.veterinaria.springboot.app.models.entity.FichaAtencion;
import org.springframework.data.repository.CrudRepository;

public interface IFichaAtencionDao extends CrudRepository<FichaAtencion, Long> {
}
