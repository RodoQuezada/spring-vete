package com.gazulabs.veterinaria.springboot.app.models.dao;

import com.gazulabs.veterinaria.springboot.app.models.entity.Paciente;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IPacienteDao extends CrudRepository<Paciente, Long> {

}
