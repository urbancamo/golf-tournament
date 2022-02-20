package uk.m0nom.golf.transform;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Uk2DateTransformerUnitTest {
    private Uk2DateTransformer transformer;

    @BeforeEach
    public void initTransformer() {
        transformer = new Uk2DateTransformer("dd/MM/yyyy");
    }

    @Test
    public void testValidDate() {
        String dbDate = transformer.transform("02/03/22");
        Assertions.assertEquals("02/03/2022", dbDate);
        Assertions.assertTrue(transformer.isValid("02/03/22"));
    }

    @Test
    public void testNullDate() {
        String dbDate = transformer.transform(null);
        Assertions.assertNull(dbDate);
        Assertions.assertFalse(transformer.isValid(null));
    }

    @Test
    public void testNotADate() {
        String dbDate = transformer.transform("aardvark");
        Assertions.assertNull(dbDate);
        Assertions.assertFalse(transformer.isValid("aardvark"));
    }
}
