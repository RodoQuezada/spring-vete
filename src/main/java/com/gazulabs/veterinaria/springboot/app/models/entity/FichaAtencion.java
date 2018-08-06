package com.gazulabs.veterinaria.springboot.app.models.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "fichas_atenciones")
public class FichaAtencion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "fecha_atencion")
    @Temporal(TemporalType.DATE)
    private Date fechaAtencion;
    @NotEmpty
    private String motivo;
    @Column(name = "estado_atencion")
    private Boolean estadoAtencion;
    private String diagnostico;

    @ManyToOne(fetch = FetchType.LAZY)
    private Paciente paciente;

    @OneToMany(mappedBy = "fichaAtencion", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    List<Atencion> atenciones;




    @PrePersist
    public void prePersist() {
        fechaAtencion = new Date();
        estadoAtencion = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFechaAtencion() {
        return fechaAtencion;
    }

    public void setFechaAtencion(Date fechaAtencion) {
        this.fechaAtencion = fechaAtencion;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Boolean getEstadoAtencion() {
        return estadoAtencion;
    }

    public void setEstadoAtencion(Boolean estadoAtencion) {
        this.estadoAtencion = estadoAtencion;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public List<Atencion> getAtenciones() {
        return atenciones;
    }

    public void setAtenciones(List<Atencion> atenciones) {
        this.atenciones = atenciones;
    }

    public static final long serialVersionUID = 1L;
}
