package com.example.application.data;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="KEIKAT")
public class Keikat extends AbstractEntity {

    private String artisti;
    private String sijainti;
    private Integer hinta;
    private LocalDateTime ajankohta;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public String getArtisti() {
        return artisti;
    }
    public void setArtisti(String artisti) {
        this.artisti = artisti;
    }
    public String getSijainti() {
        return sijainti;
    }
    public void setSijainti(String sijainti) {
        this.sijainti = sijainti;
    }
    public Integer getHinta() {
        return hinta;
    }
    public void setHinta(Integer hinta) {
        this.hinta = hinta;
    }
    public LocalDateTime getAjankohta() {
        return ajankohta;
    }
    public void setAjankohta(LocalDateTime ajankohta) {
        this.ajankohta = ajankohta;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
