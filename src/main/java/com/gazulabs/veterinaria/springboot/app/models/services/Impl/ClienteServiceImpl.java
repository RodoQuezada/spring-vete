package com.gazulabs.veterinaria.springboot.app.models.services.Impl;

import com.gazulabs.veterinaria.springboot.app.models.dao.IClienteDao;
import com.gazulabs.veterinaria.springboot.app.models.entity.Cliente;
import com.gazulabs.veterinaria.springboot.app.models.services.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ClienteServiceImpl implements IClienteService {

    @Autowired
    private IClienteDao clienteDao;

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> findAll() {
        return (List<Cliente>) clienteDao.findAll();
    }

    @Override
    @Transactional
    public void save(Cliente cliente) {
        clienteDao.save(cliente);
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente findOneById(Long id) {
        return clienteDao.findById(id).orElseThrow(() -> new EntityNotFoundException());
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente findOneByRut(Long rut) {
        return clienteDao.findOneByRut(rut);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        clienteDao.delete(clienteDao.findById(id).orElseThrow(() -> new EntityNotFoundException()));
    }

    @Override
    public Page<Cliente> findAll(Pageable pageable) {
        return clienteDao.findAll(pageable);
    }

    @Override
    public Cliente findListaPacientes(Long id) {
        return clienteDao.findListaPacientes(id);
    }


}
