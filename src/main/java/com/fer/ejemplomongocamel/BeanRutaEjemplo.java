/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fer.ejemplomongocamel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.camel.Exchange;

/**
 *
 * @author netrunner
 */
public class BeanRutaEjemplo {

    public Persona getObjPersdona(Object in) {
        Map<String, Object> row = (Map<String, Object>) in;
        Persona persona = new Persona();
        persona.setId((Long) row.get("id"));
        persona.setNombre((String) row.get("nombre"));
        return persona;

    }

    private Persona persona = new Persona();

    public Long getIdPersona(Persona p) {
        return p.getId();
    }

    public List<DetalleLista> convertirLista(Object in) {
        List<DetalleLista> lstDetalleListas = new ArrayList<>();
        List<Map<String, Object>> filas = (List<Map<String, Object>>) in;
        for (Map<String, Object> fila : filas) {
            DetalleLista detalleLista = new DetalleLista();
            detalleLista.setDtlista((Long) fila.get("dtlista"));
            detalleLista.setDtvalor((String) fila.get("dtvalor"));
            lstDetalleListas.add(detalleLista);
        }
        return lstDetalleListas;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

}
