package org.capps.testutil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class H2QueryUtil {
    public static String insertMockData(int numberOfRecords, Connection connection) {
String sql = "INSERT INTO customers (name, email, phone, address, location, delivery_base_price) VALUES (?, ?, ?, ?, ?, ?)";
   
        Random random = new Random();

    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        for (int i = 0; i < numberOfRecords; i++) {
        pstmt.setString(1, "Name" + i);
        pstmt.setString(2, "email" + i + "@example.com");
        pstmt.setString(3, "123-456-789" + i);
        pstmt.setString(4, "Address " + i);
        pstmt.setString(5, 
        String.format("{\"lat\": %.6f, \"lng\": %.6f}", random.nextDouble() * 180 - 90, random.nextDouble() * 360 - 180));
        pstmt.setDouble(6, random.nextDouble() * 100);
        pstmt.addBatch();
        }
        pstmt.executeBatch();
    } catch (SQLException e) {
        e.printStackTrace();
        return "Error inserting mock data: " + e.getMessage();
    }

    return "Successfully inserted " + numberOfRecords + " records.";
    }
}
