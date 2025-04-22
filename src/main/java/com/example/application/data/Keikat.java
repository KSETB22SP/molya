package com.example.application.data;

import jakarta.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class Keikat extends AbstractEntity {

    private String artisti;
    private String sijainti;
    private Integer hinta;
    private LocalDateTime ajankohta;

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

}
