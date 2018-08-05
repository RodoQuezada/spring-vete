package com.gazulabs.veterinaria.springboot.app.models.services.Impl;

import com.gazulabs.veterinaria.springboot.app.models.dao.ITipoUsuarioDao;
import com.gazulabs.veterinaria.springboot.app.models.entity.TipoUsuario;
import com.gazulabs.veterinaria.springboot.app.models.services.ITipoUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class TipoUsuarioServiceImpl implements ITipoUsuarioService {

    @Autowired
    private ITipoUsuarioDao tipoUsuarioDao;

    @Override
    @Transactional
    public void save(TipoUsuario tipoUsuario) {
        tipoUsuarioDao.save(tipoUsuario);
    }

    @Override
    @Transactional
    public void delete(Long id) {
       tipoUsuarioDao.delete(tipoUsuarioDao.findById(id).orElseThrow(() -> new EntityNotFoundException()));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoUsuario> findAll() {
        return (List<TipoUsuario>) tipoUsuarioDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public TipoUsuario findOneId(Long id) {
        return tipoUsuarioDao.findById(id).orElseThrow(() -> new EntityNotFoundException());
    }
}
