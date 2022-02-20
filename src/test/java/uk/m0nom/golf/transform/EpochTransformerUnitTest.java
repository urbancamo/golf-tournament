package uk.m0nom.golf.transform;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EpochTransformerUnitTest {

    private EpochTransformer epochTransformer;

    @BeforeEach
    public void initTransformer() {
        epochTransformer = new EpochTransformer("dd/MM/yyyy");
    }

    @Test
    public void testNullCase() {
        String value = epochTransformer.transform(null);
        Assertions.assertNull(value);
        Assertions.assertFalse(epochTransformer.isValid(null));
    }

    @Test
    public void testNegativeCase() {
        String value = epochTransformer.transform("-1");
        Assertions.assertNull(value);
        Assertions.assertFalse(epochTransformer.isValid("-1"));
    }

    @Test
    public void testNaNCase() {
        String value = epochTransformer.transform("aardvark");
        Assertions.assertNull(value);
        Assertions.assertFalse(epochTransformer.isValid("aardvark"));
    }


    @Test
    public void testRealValue1() {
        String value = epochTransformer.transform("1638349200");
        Assertions.assertEquals("01/12/2021", value);
        Assertions.assertTrue(epochTransformer.isValid("1638349200"));
    }


    @Test
    public void testRealValue2() {
        String value = epochTransformer.transform("1638468000");
        Assertions.assertEquals("02/12/2021", value);
        Assertions.assertTrue(epochTransformer.isValid("1638468000"));
    }
}
