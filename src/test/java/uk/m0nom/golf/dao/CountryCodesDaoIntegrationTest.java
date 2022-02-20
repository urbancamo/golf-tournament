package uk.m0nom.golf.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Requires local instance of DynamoDB to be running with a populated country codes table
 * TODO: use programmatic startup, populate, shutdown of DynamoDB
 */
@SpringBootTest
public class CountryCodesDaoIntegrationTest {
    private final CountryCodeDao dao;

    @Autowired
    public CountryCodesDaoIntegrationTest(CountryCodeDao dao) {
        this.dao = dao;
    }

    @Test
    public void getCountryNameFromCodeUk() {
        String value = dao.getCountryNameFromCode("GB");
        assertNotNull(value);
        assertEquals("United Kingdom", value);
    }


    @Test
    public void getCountryNameFromCodeUs() {
        String value = dao.getCountryNameFromCode("US");
        assertNotNull(value);
        assertEquals("United States", value);
    }


    @Test
    public void getCountryNameFromCodeFr() {
        String value = dao.getCountryNameFromCode("FR");
        assertNotNull(value);
        assertEquals("France", value);
    }

}
