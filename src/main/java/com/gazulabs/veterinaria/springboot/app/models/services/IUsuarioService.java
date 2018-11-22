package com.gazulabs.veterinaria.springboot.app.models.services;


import com.gazulabs.veterinaria.springboot.app.models.entity.Usuario;

import java.util.List;

public interface IUsuarioService  {

    public void save(Usuario usuario);

    public void delete(Long id);

    public Usuario findById(Long id);

    public List<Usuario> findAll();

}
