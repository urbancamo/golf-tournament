package uk.m0nom.golf.transform;

import uk.m0nom.golf.dao.CountryCodeDao;

public class CountryAlpha2Transformer implements Transformer {
    private final CountryCodeDao countryCodeDao;

    public CountryAlpha2Transformer(CountryCodeDao countryCodeDao) {
        this.countryCodeDao = countryCodeDao;
    }

    @Override
    public String transform(String code) {
        return countryCodeDao.getCountryNameFromCode(code);
    }

    @Override
    public boolean isValid(String code) {
        return transform(code) != null;
    }


}
