package uk.m0nom.golf.transform;

import org.springframework.stereotype.Component;
import uk.m0nom.golf.dao.CountryCodeDao;

import java.util.HashMap;
import java.util.Map;

@Component
public class TransformerFactory {

    private final Map<String, Transformer> transformerMap;

    public TransformerFactory(TransformerConfig config, CountryCodeDao countryCodeDao) {
        transformerMap = new HashMap<>();
        transformerMap.put("epoch", new EpochTransformer(config.getDateFormat()));
        transformerMap.put("uk2Date", new Uk2DateTransformer(config.getDateFormat()));
        transformerMap.put("countryAlpha2", new CountryAlpha2Transformer(countryCodeDao));
    }

    public Transformer getTransformer(String dataType) {
        return transformerMap.get(dataType);
    }
}
