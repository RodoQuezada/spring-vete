package com.gazulabs.veterinaria.springboot.app.models.services.Impl;

import com.gazulabs.veterinaria.springboot.app.models.dao.IRazaDao;
import com.gazulabs.veterinaria.springboot.app.models.entity.Raza;
import com.gazulabs.veterinaria.springboot.app.models.services.IRazaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class RazaServiceImpl implements IRazaService {

    @Autowired
    private IRazaDao razaDao;

    @Override
    @Transactional
    public void save(Raza raza) {
        razaDao.save(raza);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        razaDao.delete(findById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Raza> findAll() {
        return (List<Raza>) razaDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Raza findById(Long id) {
       // return razaDao.findById(id).orElseThrow(() -> new EntityNotFoundException());
        return razaDao.fetchWithEspecie(id);
    }



}
