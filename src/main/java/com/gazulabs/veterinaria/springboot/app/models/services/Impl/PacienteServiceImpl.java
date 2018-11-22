package com.gazulabs.veterinaria.springboot.app.models.services.Impl;

import com.gazulabs.veterinaria.springboot.app.models.dao.IPacienteDao;
import com.gazulabs.veterinaria.springboot.app.models.entity.Paciente;
import com.gazulabs.veterinaria.springboot.app.models.services.IPacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class PacienteServiceImpl implements IPacienteService {

    @Autowired
    private IPacienteDao pacienteDao;

    @Override
    @Transactional
    public void save(Paciente paciente) {
        pacienteDao.save(paciente);
    }

    @Override
    @Transactional(readOnly = true)
    public Paciente findById(Long id) {
        return pacienteDao.findById(id).orElseThrow(() -> new EntityNotFoundException());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        pacienteDao.delete(pacienteDao.findById(id).orElseThrow(() -> new EntityNotFoundException()));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Paciente> findAll() {
        return (List<Paciente>) pacienteDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Paciente> findAllPacienteByRutCliente(int rut) {
        return null;
    }
}
