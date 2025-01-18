package org.capps.batch;

import java.util.List;
import javax.inject.Named;

import org.capps.dto.ItemDto;

import io.agroal.api.AgroalDataSource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@ApplicationScoped
@jakarta.inject.Named
public class MyItemWriter extends jakarta.batch.api.chunk.AbstractItemWriter {

    @Inject
    private javax.sql.DataSource dataSource;

    public static final String SQL_UPDATE = "UPDATE customers SET delivery_base_price = ? WHERE id = ?";

    @Override
    public void writeItems(List<Object> items) throws Exception {
        try (Connection connection = dataSource.getConnection()) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}