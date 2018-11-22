package com.gazulabs.veterinaria.springboot.app.models.services.Impl;

import com.gazulabs.veterinaria.springboot.app.models.dao.IFichaAtencionDao;
import com.gazulabs.veterinaria.springboot.app.models.entity.FichaAtencion;
import com.gazulabs.veterinaria.springboot.app.models.services.IFichaAtencionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FichaAtencionServiceImpl implements IFichaAtencionService {

    @Autowired
    private IFichaAtencionDao fichaAtencionDao;

    @Override
    @Transactional
    public void save(FichaAtencion fichaAtencion) {
        fichaAtencionDao.save(fichaAtencion);
    }

    @Override
    @Transactional(readOnly = true)
    public FichaAtencion findById(Long id) {
        return fichaAtencionDao.findById(id).orElseThrow(() -> new NullPointerException());
    }

    @Override
    @Transactional
    public void delete(Long id) {
            fichaAtencionDao.delete(fichaAtencionDao.findById(id).orElseThrow(() -> new NullPointerException()));
    }

    @Override
    @Transactional(readOnly = true)
    public List<FichaAtencion> findAll() {
        return (List<FichaAtencion>) fichaAtencionDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<FichaAtencion> buscarFichaPorEstadoAtencion(char estado) {
        return fichaAtencionDao.buscarFichaPorEstadoAtencion(estado);
    }


}
