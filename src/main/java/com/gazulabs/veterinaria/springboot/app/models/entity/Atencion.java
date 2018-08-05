package com.gazulabs.veterinaria.springboot.app.models.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "atenciones")
public class Atencion implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private double peso;
    private double temperatura;
    @Column(name = "signos_vitales")
    private double signosVitales;

    @Column(name = "create_at")
    @Temporal(TemporalType.DATE)
    private Date createAt;

    private String comentario;

    @ManyToOne(fetch = FetchType.LAZY)
    private FichaAtencion fichaAtencion;

    @PrePersist
    public void prePersist(){
        createAt = new Date();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public double getSignosVitales() {
        return signosVitales;
    }

    public void setSignosVitales(double signosVitales) {
        this.signosVitales = signosVitales;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public FichaAtencion getFichaAtencion() {
        return fichaAtencion;
    }

    public void setFichaAtencion(FichaAtencion fichaAtencion) {
        this.fichaAtencion = fichaAtencion;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public static final long serialVersionUID = 1L;
}
