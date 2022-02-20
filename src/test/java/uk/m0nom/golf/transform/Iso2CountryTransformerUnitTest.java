package uk.m0nom.golf.transform;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import uk.m0nom.golf.dao.CountryCodeDao;

import static org.mockito.Mockito.when;

public class Iso2CountryTransformerUnitTest {
    @Mock
    private CountryCodeDao countryCodeDao;

    private Iso2CountryTransformer transformer;

    @BeforeEach
    public void initTransformer() {
        countryCodeDao = Mockito.mock(CountryCodeDao.class);
        transformer = new Iso2CountryTransformer(countryCodeDao);
        when(countryCodeDao.getCountryNameFromCode("GB")).thenReturn("United Kingdom");
        when(countryCodeDao.getCountryNameFromCode(null)).thenReturn(null);
        when(countryCodeDao.getCountryNameFromCode("Aa")).thenReturn(null);
    }

    @Test
    public void testGb() {
        String name = transformer.transform("GB");
        Assertions.assertEquals("United Kingdom", name);
        Assertions.assertTrue(transformer.isValid("GB"));
    }

    @Test
    public void testNull() {
        String name = transformer.transform(null);
        Assertions.assertNull(name);
        Assertions.assertFalse(transformer.isValid(null));
    }

    @Test
    public void testUnknown() {
        String name = transformer.transform("Aa");
        Assertions.assertNull(name);
        Assertions.assertFalse(transformer.isValid("Aa"));
    }
}
