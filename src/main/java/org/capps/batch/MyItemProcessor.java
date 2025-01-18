package org.capps.batch;

import javax.batch.api.chunk.ItemProcessor;
import javax.inject.Named;

import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
@jakarta.inject.Named
public class MyItemProcessor implements jakarta.batch.api.chunk.ItemProcessor {

    @Override
    public String processItem(Object item) throws Exception {
        // Example processing: convert item to uppercase
        System.out.println("Processing item: " + item);
        return item.toString().toUpperCase();
    }
}