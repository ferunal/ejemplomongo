/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fer.ejemplomongocamel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author netrunner
 */
public class Persona implements Serializable{
    private Long id;
    private String nombre;

    public Long getId() {
        return id;
    }
    
    private List<DetalleLista> detalleListas = new ArrayList();

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<DetalleLista> getDetalleListas() {
        return detalleListas;
    }

    public void setDetalleListas(List<DetalleLista> detalleListas) {
        this.detalleListas = detalleListas;
    }
}
