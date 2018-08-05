package com.gazulabs.veterinaria.springboot.app.models.dao;

import com.gazulabs.veterinaria.springboot.app.models.entity.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface IUsuarioDao extends CrudRepository<Usuario, Long> {
}
