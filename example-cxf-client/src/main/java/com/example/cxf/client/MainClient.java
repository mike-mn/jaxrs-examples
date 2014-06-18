package com.example.cxf.client;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.binding.BindingFactoryManager;
import org.apache.cxf.jaxrs.JAXRSBindingFactory;
import org.apache.cxf.jaxrs.client.JAXRSClientFactoryBean;
import org.apache.cxf.jaxrs.client.WebClient;
import com.example.jaxrs.sales.enablement.api.model.Presentation;
import com.example.jaxrs.sales.enablement.api.model.Presentations;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

public class MainClient {

    public static void main(String[] args) {
        String baseAddress = "http://localhost:8181";

        List<Object> providers = new ArrayList<Object>();
        providers.add( new JacksonJaxbJsonProvider() );

        // Proxy method
        System.out.println("JAXRS Proxy");
        com.example.jaxrs.sales.enablement.api.resource.Presentations service = createProxy(baseAddress, providers, com.example.jaxrs.sales.enablement.api.resource.Presentations.class);
        Response response = service.getPresentations("s3cr3t", "Some title", null, null);
        Presentations entity = response.readEntity(Presentations.class);
        printPresentations(entity);
        
        
        // Web client
        System.out.println("WebClient");
        WebClient client = WebClient.create(baseAddress, providers);
        Presentations presentations = client.path("presentations")
                .header("Authorization", "s3cr3t")
                .query("title", "Some title")
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .get(Presentations.class);
        printPresentations(presentations);

    }

    private static <T> T createProxy(String baseAddress, List<Object> providers, Class<T> resourceClass) {
        JAXRSClientFactoryBean clientFactory = new JAXRSClientFactoryBean();
        clientFactory.setProviders(providers);
        clientFactory.setResourceClass(resourceClass);
        clientFactory.setAddress(baseAddress);
        BindingFactoryManager manager = clientFactory.getBus().getExtension(BindingFactoryManager.class);
        JAXRSBindingFactory factory = new JAXRSBindingFactory();
        factory.setBus(clientFactory.getBus());
        manager.registerBindingFactory(JAXRSBindingFactory.JAXRS_BINDING_ID, factory);
        return clientFactory.create(resourceClass);
    }

    private static void printPresentations(Presentations entity) {
        for (Presentation item : entity.getPresentations()) {
            System.out.println("item\t" + item.getId() + "\t" + item.getTitle());
        }
        System.out.println();
    }

}
