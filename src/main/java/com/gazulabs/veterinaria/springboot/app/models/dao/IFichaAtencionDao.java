package com.gazulabs.veterinaria.springboot.app.models.dao;

import com.gazulabs.veterinaria.springboot.app.models.entity.FichaAtencion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IFichaAtencionDao extends CrudRepository<FichaAtencion, Long> {

  @Query("select f from FichaAtencion f where f.estadoAtencion = ?1")
  public List<FichaAtencion> buscarFichaPorEstadoAtencion(char estado);


  // @Query("select r from Raza r left join fetch r.especie e where r.id = ?1")

}
