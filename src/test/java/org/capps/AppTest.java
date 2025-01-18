package org.capps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.capps.testutil.FaultyDataSource;
import org.capps.testutil.H2QueryUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */

@QuarkusTest
public class AppTest {
    @Inject
    FaultyDataSource dataSource;

    @Inject
    App app;

    /**
     * Rigourous Test :-)
     */

    @BeforeEach
    public void setUp() {
        try (Connection connection = dataSource.getConnection();) {

            H2QueryUtil.insertMockData(1, connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testApp() throws Exception {
        app.run((String[]) null);
        try (Connection connection = dataSource.getConnection();) {

            List<Map<String, Object>> res = H2QueryUtil
                    .executeQuery("select * from customers where delivery_base_price > 10", connection);
            System.out.println(res);
            assertEquals(res.size(), 1);
        } 
    }
}
