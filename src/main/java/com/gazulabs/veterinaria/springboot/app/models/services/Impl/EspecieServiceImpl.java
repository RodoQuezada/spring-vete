package com.gazulabs.veterinaria.springboot.app.models.services.Impl;

import com.gazulabs.veterinaria.springboot.app.models.dao.IEspecieDao;
import com.gazulabs.veterinaria.springboot.app.models.entity.Especie;
import com.gazulabs.veterinaria.springboot.app.models.services.IEspecieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class EspecieServiceImpl implements IEspecieService {

    @Autowired
    private IEspecieDao especieDao;

    @Override
    @Transactional
    public void save(Especie especie) {
        especieDao.save(especie);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Especie> findAll() {
        return (List<Especie>) especieDao.findAll();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        especieDao.delete(especieDao.findById(id).orElseThrow(() -> new EntityNotFoundException()));
    }

    @Override
    @Transactional(readOnly = true)
    public Especie findById(Long id) {
        return especieDao.findById(id).orElseThrow(() -> new EntityNotFoundException());
    }
}
