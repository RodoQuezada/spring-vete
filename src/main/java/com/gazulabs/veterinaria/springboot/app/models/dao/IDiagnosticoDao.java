package com.gazulabs.veterinaria.springboot.app.models.dao;

import com.gazulabs.veterinaria.springboot.app.models.entity.Diagnostico;
import org.springframework.data.repository.CrudRepository;

public interface IDiagnosticoDao extends CrudRepository<Diagnostico, Long> {
}
