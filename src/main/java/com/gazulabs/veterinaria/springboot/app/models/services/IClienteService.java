package com.gazulabs.veterinaria.springboot.app.models.services;

import com.gazulabs.veterinaria.springboot.app.models.entity.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface IClienteService {

    public List<Cliente> findAll();

    public void save (Cliente cliente);

    public Cliente findOneById(Long id);

    public Cliente findOneByRut(Long rut);

    public void delete(Long id);

    public Page<Cliente> findAll(Pageable pageable);

    public Cliente findListaPacientes(Long id);


}
