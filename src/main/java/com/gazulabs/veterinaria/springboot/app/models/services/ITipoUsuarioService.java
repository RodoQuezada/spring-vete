package com.gazulabs.veterinaria.springboot.app.models.services;

import com.gazulabs.veterinaria.springboot.app.models.entity.TipoUsuario;

import java.util.List;

public interface ITipoUsuarioService {

    public void save(TipoUsuario tipoUsuario);

    public void delete(Long id);

    public List<TipoUsuario> findAll();

    public TipoUsuario findOneId(Long id);

}
