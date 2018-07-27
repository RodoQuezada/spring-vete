package com.gazulabs.veterinaria.springboot.app.models.services;

import com.gazulabs.veterinaria.springboot.app.models.entity.Cliente;

import java.util.List;


public interface IClienteService {

    public List<Cliente> findAll();

    public void save (Cliente cliente);

    public Cliente findOneById(Long id);

    public Cliente findOneByRut(Long rut);

    public void delete(Long id);


}
