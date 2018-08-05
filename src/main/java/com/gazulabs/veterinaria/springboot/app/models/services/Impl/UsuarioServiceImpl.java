package com.gazulabs.veterinaria.springboot.app.models.services.Impl;

import com.gazulabs.veterinaria.springboot.app.models.dao.IUsuarioDao;
import com.gazulabs.veterinaria.springboot.app.models.entity.Usuario;
import com.gazulabs.veterinaria.springboot.app.models.services.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    @Autowired
    private IUsuarioDao usuarioDao;

    @Override
    public void save(Usuario usuario) {
          usuarioDao.save(usuario);
    }

    @Override
    public void delete(Long id) {
          usuarioDao.delete(usuarioDao.findById(id).orElseThrow(() -> new EntityNotFoundException()));
    }

    @Override
    public Usuario findById(Long id) {
        return usuarioDao.findById(id).orElseThrow(() -> new EntityNotFoundException());
    }

    @Override
    public List<Usuario> findAll() {
        return (List<Usuario>) usuarioDao.findAll();
    }
}
