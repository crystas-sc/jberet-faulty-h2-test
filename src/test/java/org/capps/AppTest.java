package org.capps;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.capps.batch.MyItemWriter;
import org.capps.testutil.FaultyDataSource;
import org.capps.testutil.FaultyState;
import org.capps.testutil.H2QueryUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkiverse.jberet.runtime.QuarkusJobOperator;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

/**
 * Unit test for simple App.
 */

@QuarkusTest
public class AppTest {

    @Inject
    FaultyDataSource dataSource;

    @jakarta.inject.Inject
    QuarkusJobOperator jobOperator;

    /**
     * Rigourous Test :-)
     */

    @BeforeEach
    public void setUp() {

        try (Connection connection = dataSource.getConnection();) {
            H2QueryUtil.deleteTable("customers", connection);
            H2QueryUtil.insertMockData(20, connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testApp() throws Exception {
        new App(jobOperator).runJob();
        try (Connection connection = dataSource.getConnection();) {

            List<Map<String, Object>> res = H2QueryUtil
                    .executeQuery("select * from customers where delivery_base_price > 10", connection);
            System.out.println(res);
            assertEquals(20, res.size());
        }
    }

    @Test
    public void testAppWithFaultyDataSource() throws Exception {
        // given
        ((FaultyDataSource) dataSource).setFaultableQueries(Set.of(FaultyState.builder()
                .sql(MyItemWriter.SQL_UPDATE)
                .faultyIndexes(Set.of(5))
                .currentIndex(0)
                .build()));

        // when
        new App(jobOperator).runJob();

        // then
        try (Connection connection = dataSource.getConnection();) {

            List<Map<String, Object>> res = H2QueryUtil
                    .executeQuery("select * from customers where delivery_base_price > 10", connection);
            System.out.println(res);
            assertEquals(10, res.size());
        }
    }
}
