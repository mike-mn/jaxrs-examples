package com.example.cxf.server;

import java.util.Arrays;
import java.util.Scanner;

import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSBindingFactory;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import com.example.cxf.server.impl.PresentationResource;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

public class MainServer {

    private static Server start(Object... serviceBeans) {
        JAXRSServerFactoryBean rsFactory = new JAXRSServerFactoryBean();
        rsFactory.setBindingId(JAXRSBindingFactory.JAXRS_BINDING_ID);
        rsFactory.setAddress("http://localhost:8181");
        rsFactory.setServiceBeans(Arrays.asList(serviceBeans));
        rsFactory.setProvider(new JacksonJsonProvider());
        return rsFactory.create();
    }

    public static void main(String[] args) {
        Server server = start(new PresentationResource());
        System.out.println("server endpoint: " + server.getEndpoint().getEndpointInfo().getAddress());
        System.out.println("Strike ENTER to stop...");
        new Scanner(System.in).nextLine();
        
        server.stop();

        System.out.println("Bye!");
        System.exit(0);

    }

}
