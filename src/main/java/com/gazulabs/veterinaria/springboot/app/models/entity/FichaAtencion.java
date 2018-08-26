package com.gazulabs.veterinaria.springboot.app.models.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
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

    /*
    Nomenclatura de estado atencion:
    - P: Por atender (Paciente se encuentra en sala de espera).
    - A: Atendiendo (Paciente se encuentra en observación o en atención).
    - F: Finalizado (Paciente de alta).
     */
    @Column(name = "estado_atencion")
    private Character estadoAtencion;

    /*
     Nomenclatura de estado pago:
     -False: No se ha realizado el pago.
     -True: Se encuentra pagada
     */
    @Column(name = "estado_pago")
    private Boolean estadoPago;

    private String diagnostico;

    @ManyToOne(fetch = FetchType.LAZY)
    private Paciente paciente;

    @OneToMany(mappedBy = "fichaAtencion", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    List<Atencion> atenciones;

    @PrePersist
    public void prePersist() {
        this.fechaAtencion = new Date();
    }

    public FichaAtencion() {
        this.estadoAtencion = 'p';
        this.estadoPago = false;
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

    public char getEstadoAtencion() {
        return estadoAtencion;
    }

    public void setEstadoAtencion(char estadoAtencion) {
        this.estadoAtencion = estadoAtencion;
    }

    public Boolean getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(Boolean estadoPago) {
        this.estadoPago = estadoPago;
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
