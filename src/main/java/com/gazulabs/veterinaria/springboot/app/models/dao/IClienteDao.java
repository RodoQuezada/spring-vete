package com.gazulabs.veterinaria.springboot.app.models.dao;


import com.gazulabs.veterinaria.springboot.app.models.entity.Cliente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface IClienteDao extends PagingAndSortingRepository<Cliente, Long> {

    @Query("select c from Cliente c where c.rut=?1")
    public Cliente findOneByRut(Long rut);

    @Query("select c from Cliente  c left join fetch c.pacientes p where c.id=?1")
    public Cliente findListaPacientes(Long id);

   // @Query("select r from Raza r left join fetch r.especie e where r.id = ?1")


}
