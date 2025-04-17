package com.example.gatewayservice.config;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class H2ConsoleServer {

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> h2ConsoleServerCustomizer() {
        return factory -> {
            factory.addAdditionalTomcatConnectors(createH2Connector());
        };
    }

    private org.apache.catalina.connector.Connector createH2Connector() {
        org.apache.catalina.connector.Connector connector = new org.apache.catalina.connector.Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
        connector.setPort(8081);
        connector.setScheme("http");
        return connector;
    }
} 