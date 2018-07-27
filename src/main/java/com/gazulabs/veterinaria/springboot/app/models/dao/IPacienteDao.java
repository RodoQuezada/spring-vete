package com.gazulabs.veterinaria.springboot.app.models.dao;

import com.gazulabs.veterinaria.springboot.app.models.entity.Paciente;
import org.springframework.data.repository.CrudRepository;

public interface IPacienteDao extends CrudRepository<Paciente, Long> {

}
