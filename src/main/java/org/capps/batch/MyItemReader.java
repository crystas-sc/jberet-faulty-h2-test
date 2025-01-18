package org.capps.batch;

import java.util.List;

import javax.inject.Named;

import jakarta.batch.api.chunk.ItemReader;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.Serializable;
import java.util.ArrayList;


@ApplicationScoped
@jakarta.inject.Named
public class MyItemReader implements ItemReader {

    private List<String> items;
    private int currentIndex;

    public MyItemReader() {
        this.items = new ArrayList<>();
        this.currentIndex = 0;
        // Add some sample items
        items.add("Item1");
        items.add("Item2");
        items.add("Item3");
    }

    public Object readItem() {
        if (currentIndex < items.size()) {
            return items.get(currentIndex++);
        } else {
            return null; // No more items to read
        }
    }

    @Override
    public void open(Serializable checkpoint) throws Exception {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'open'");
    }

    @Override
    public void close() throws Exception {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'close'");
    }

    @Override
    public Serializable checkpointInfo() throws Exception {
        return currentIndex; // Return the current index as a checkpoint
    }

  
}