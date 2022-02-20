package uk.m0nom.golf.service;

import org.springframework.stereotype.Service;
import uk.m0nom.golf.dao.TransformDao;
import uk.m0nom.golf.domain.Transform;
import uk.m0nom.golf.domain.Tournament;
import uk.m0nom.golf.transform.Transformer;
import uk.m0nom.golf.transform.TransformerFactory;

import java.util.Map;

@Service
public class TransformService {
    private final TransformDao transformDao;
    private final TransformerFactory transformerFactory;

    public TransformService(TransformDao transformDao, TransformerFactory transformerFactory) {
        this.transformDao = transformDao;
        this.transformerFactory = transformerFactory;
    }

    public Tournament transformForPersistence(Tournament tournament) {
        Integer source = tournament.getSource();
        Map<String, String> data = tournament.getData();
        Tournament result = new Tournament(tournament.getSource());

        for (String key : data.keySet()) {
            Transform transform = transformDao.getTransform(source, key);
            if (transform != null) {
                String value = data.get(key);

                Transformer transformer = transformerFactory.getTransformer(transform.getValueTransformer());
                if (transformer != null) {
                    value = transformer.transform(value);
                }
                result.put(transform.getMappedKey(), value);
            } else {
                // No transform defined, insert raw data
                result.put(key, data.get(key));
            }
        }
        return result;
    }

}
