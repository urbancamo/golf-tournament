package uk.m0nom.golf.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


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
}
