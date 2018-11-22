package com.gazulabs.veterinaria.springboot.app.models.services.Impl;

import com.gazulabs.veterinaria.springboot.app.models.dao.IAtencionDao;
import com.gazulabs.veterinaria.springboot.app.models.entity.Atencion;
import com.gazulabs.veterinaria.springboot.app.models.services.IAtencionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class AtencionServiceImpl implements IAtencionService {

    @Autowired
    private IAtencionDao atencionDao;

    @Override
    @Transactional
    public void save(Atencion atencion) {
        atencionDao.save(atencion);
    }

    @Override
    @Transactional(readOnly = true)
    public Atencion findOneById(Long id) {
        return atencionDao.findById(id).orElseThrow(() -> new EntityNotFoundException());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        atencionDao.delete(findOneById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Atencion> findAll() {
        return (List<Atencion>) atencionDao.findAll();
    }
}
