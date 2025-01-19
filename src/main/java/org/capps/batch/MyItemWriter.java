package org.capps.batch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import org.capps.dto.ItemDto;

import io.agroal.api.AgroalDataSource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;

@Dependent
@jakarta.inject.Named
public class MyItemWriter extends jakarta.batch.api.chunk.AbstractItemWriter {

    @Inject
    private AgroalDataSource dataSource;

    public static final String SQL_UPDATE = "UPDATE customers SET delivery_base_price = ? WHERE id = ?";

    @Override
    public void writeItems(List<Object> items) throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try {
                for (Object item : items) {
                    ItemDto itemDto = (ItemDto) item;
                    // Write each item to the desired output
                    System.out.println("Writing item: " + item);

                    try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
                        statement.setBigDecimal(1, itemDto.getCost());
                        statement.setLong(2, itemDto.getId());
                        statement.executeUpdate();
                    }
                }
                connection.commit();
                connection.setAutoCommit(true);
            } catch (Exception e) {
                e.printStackTrace();
                connection.rollback();
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

}