/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fer.ejemplomongocamel;

import org.apache.camel.Exchange;
import org.apache.camel.Expression;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.language.SimpleExpression;

/**
 *
 * @author netrunner
 */
public class RutaEjemplo extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        
        from("timer:jdbcCamel?repeatCount=1").
                setBody(new SimpleExpression("select * from lista")).
                to("jdbc:dsPruebaCamel").split().
                body().
                //to("bean:bre?method=getObjPersdona(${body})").
                // marshal().json(JsonLibrary.Jackson, Persona.class).
               // log("${body}").
                to("activemq:cola:persona");

        //to("mongodb:dsMongo?database=polinotificador&collection=lista&operation=insert");
        from("activemq:cola:persona").
                to("bean:bre?method=getObjPersdona(${body})").
               // log("persona: ${body}").
                enrich("direct:detalle", new PersonaAgregador()).
                //to("direct:detalle").
                marshal().json(JsonLibrary.Jackson, Persona.class).
                log("Salida json: ${body}").
                to("mongodb:dsMongo?database=polinotificador&collection=persona&operation=insert");
               // .to("mock:salida");
//        from("timer:hola?repeatCount=1").
//                log("Iniciar").
//                to("mongodb:dsMongo?database=polinotificador&operation=getDbStats").
//                log("Hola mongo ${body}");
        from("direct:detalle").setHeader("idlista", new SimpleExpression(  "${body.getId}")).
                //log("Cabecera: ${header.idlista}").
                setBody(new SimpleExpression("select dtlista, dtvalor from detalle_lista where idlista =cast(:?idlista as integer)"))
                .to("jdbc:dsPruebaCamel?useHeadersAsParameters=true").to("bean:bre?method=convertirLista(${body})");
    }

}
