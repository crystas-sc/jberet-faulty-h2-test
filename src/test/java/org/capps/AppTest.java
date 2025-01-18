package org.capps;

import static org.junit.jupiter.api.Assertions.assertTrue;

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
    App app;
    /**
     * Rigourous Test :-)
     */
    @Test
    public void testApp() {
        app.run((String[]) null);
        assertTrue(true);
    }
}
