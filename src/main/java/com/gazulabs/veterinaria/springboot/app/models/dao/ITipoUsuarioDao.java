package com.gazulabs.veterinaria.springboot.app.models.dao;

import com.gazulabs.veterinaria.springboot.app.models.entity.TipoUsuario;
import org.springframework.data.repository.CrudRepository;

public interface ITipoUsuarioDao extends CrudRepository<TipoUsuario, Long> {

}
