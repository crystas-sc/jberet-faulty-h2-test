package org.capps.batch;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import io.agroal.api.AgroalDataSource;
import io.quarkus.arc.Arc;
import jakarta.batch.api.chunk.ItemReader;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.spi.Bean;
import jakarta.inject.Inject;

@Dependent
@jakarta.inject.Named
public class MyItemReader implements ItemReader {
    @Inject
    private AgroalDataSource dataSource;

    private List<String> items;
    private int currentIndex;

    public MyItemReader() {
        this.items = new ArrayList<>();
        this.currentIndex = 0;
        // Add some sample items
        // items.add("Item1");
        // items.add("Item2");
        // items.add("Item3");
    }

    public Object readItem() {
        if (currentIndex < items.size()) {
            return items.get(currentIndex++);
        } else {
            return null; // No more items to read
        }
    }

    public void listAllBeans() {
        Set<Bean<?>> beans = Arc.container().beanManager().getBeans(Object.class);
        System.out.println("Registered Beans in Arc Container:");
        for (Bean<?> bean : beans) {
            System.out.println(" - Bean: " + bean.getBeanClass().getName() +
                    ", Scope: " + bean.getScope().getName());
        }
    }

    @Override
    public void open(Serializable checkpoint) throws Exception {

        listAllBeans();

        String sql = "SELECT json_build_object('id', id, 'lat',jsonb_extract_path(location,'lat'),'lng',jsonb_extract_path(location,'lng')) as input FROM customers";
        System.out.println("item reader Datasource class: " + dataSource.getClass().getCanonicalName());
        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                items.add(resultSet.getString("input"));
            }

        }
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