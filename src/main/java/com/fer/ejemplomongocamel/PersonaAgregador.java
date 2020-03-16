/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fer.ejemplomongocamel;

import java.util.ArrayList;
import java.util.List;
import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;

/**
 *
 * @author netrunner
 */
public class PersonaAgregador implements AggregationStrategy{

    @Override
    public Exchange aggregate(Exchange original, Exchange recurso) {
        Persona persona = original.getIn().getBody(Persona.class);
        List<DetalleLista> lstDetalleListas = recurso.getMessage().getBody(new ArrayList<DetalleLista>().getClass());
        persona.setDetalleListas(lstDetalleListas);
//        System.out.println("Mensaje recurso: " +recurso.getMessage());
//        System.out.println("original " + persona);
//        System.out.println("recurso " + lstDetalleListas);
        if (original.getPattern().isOutCapable()) {
            original.getMessage().setBody(persona);
        } else {
            original.getIn().setBody(persona);
        }
        return original;
        
    }
    
}
