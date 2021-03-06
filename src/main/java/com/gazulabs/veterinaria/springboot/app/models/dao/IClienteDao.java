package com.gazulabs.veterinaria.springboot.app.models.dao;


import com.gazulabs.veterinaria.springboot.app.models.entity.Cliente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface IClienteDao extends PagingAndSortingRepository<Cliente, Long> {

    @Query("select c from Cliente c where c.rut=?1")
    public Cliente findOneByRut(Long rut);

}
