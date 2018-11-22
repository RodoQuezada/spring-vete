package com.gazulabs.veterinaria.springboot.app.models.dao;

import com.gazulabs.veterinaria.springboot.app.models.entity.Raza;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface IRazaDao extends CrudRepository<Raza, Long> {

    @Query("select r from Raza r left join fetch r.especie e where r.id = ?1")
    public Raza fetchWithEspecie(Long id);

}
