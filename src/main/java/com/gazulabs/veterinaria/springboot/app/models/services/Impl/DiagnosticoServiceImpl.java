package com.gazulabs.veterinaria.springboot.app.models.services.Impl;

import com.gazulabs.veterinaria.springboot.app.models.dao.IDiagnosticoDao;
import com.gazulabs.veterinaria.springboot.app.models.entity.Diagnostico;
import com.gazulabs.veterinaria.springboot.app.models.services.IDiagnosticoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class DiagnosticoServiceImpl implements IDiagnosticoService {

   @Autowired
   private IDiagnosticoDao diagnosticoDao;


    @Override
    @Transactional
    public void save(Diagnostico diagnostico) {
        diagnosticoDao.save(diagnostico);
    }

    @Override
    @Transactional(readOnly = true)
    public Diagnostico findOneById(Long id) {
        return diagnosticoDao.findById(id).orElseThrow(() -> new EntityNotFoundException());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        diagnosticoDao.delete(findOneById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Diagnostico> findAll() {
        return (List<Diagnostico>) diagnosticoDao.findAll();
    }
}
