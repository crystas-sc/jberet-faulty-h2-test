package org.capps.batch;

import java.util.List;
import javax.inject.Named;

import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
@jakarta.inject.Named
public class MyItemWriter extends jakarta.batch.api.chunk.AbstractItemWriter {



    @Override
    public void writeItems(List<Object> items) throws Exception {
        for (Object item : items) {
            // Write each item to the desired output
            System.out.println("Writing item: " + item);
        }
    }


}