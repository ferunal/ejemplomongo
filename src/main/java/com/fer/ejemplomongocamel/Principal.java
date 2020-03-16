/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fer.ejemplomongocamel;

import com.mongodb.Mongo;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import java.lang.annotation.Documented;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.component.mongodb.MongoDbComponent;
import org.apache.camel.component.mongodb.MongoDbComponentConfigurer;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 *
 * @author netrunner
 */
public class Principal {

    public static void main(String[] args) {
        try {

            BasicDataSource dataSource = new BasicDataSource();
            dataSource.setUrl("jdbc:postgresql://localhost:5432/pruebacamel");
            dataSource.setDriverClassName("org.postgresql.Driver");
            dataSource.setUsername("auditoria");
            dataSource.setPassword("auditoria");

              
            ActiveMQConnectionFactory amqcf = new ActiveMQConnectionFactory();
            amqcf.setBrokerURL("vm://localhost?broker.presistence=false");
            amqcf.setTrustAllPackages(true);
            PooledConnectionFactory pcf = new PooledConnectionFactory();
            pcf.setMaxConnections(100);
            pcf.setConnectionFactory(amqcf);           
            
            

            dataSource.setInitialSize(4);
            dataSource.setMaxIdle(4);
            dataSource.setMaxTotal(16);

            BeanRutaEjemplo beanRutaEjemplo = new BeanRutaEjemplo();

            MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
            org.apache.camel.component.mongodb.MongoDbComponent dbComponent = new MongoDbComponent();
            dbComponent.setMongoConnection(mongoClient);

//            Mongo mongoClient = new Mongo("localhost");
            CamelContext context = new DefaultCamelContext();
            context.setStreamCaching(Boolean.TRUE);
            Map<String, Object> properties = new HashMap<>();
            properties.put("camelContext", context);
            properties.put("database", "polinotificador");
            // dbComponent.createEndpoint("mongodbfer", properties);

            //    dbComponent.setCamelContext(context);
            context.getRegistry().bind("dsMongo", mongoClient);
            context.getRegistry().bind("dsPruebaCamel", dataSource);
            context.getRegistry().bind("bre", beanRutaEjemplo);
            context.getRegistry().bind("amqcf", amqcf);
            context.getRegistry().bind("pcf", pcf);
            //dbComponent.setCamelContext(context);
//            context.addComponent("mongoComp", dbComponent);

            RutaEjemplo re = new RutaEjemplo();
            context.addRoutes(re);
            context.start();
            System.out.println("Esperar días parar contexto");
            Thread.sleep(1000 * 60 * 60 * 12 * 8);
            context.stop();
        } catch (Exception ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
