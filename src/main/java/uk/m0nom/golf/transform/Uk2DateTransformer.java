package uk.m0nom.golf.transform;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Converts a UK date in the form dd/mm/yy to the internal representation
 */
public class Uk2DateTransformer implements Transformer {
    private final DateFormat dateFormat;
    private final DateFormat uk2DateFormat = new SimpleDateFormat("dd/MM/yy");

    public Uk2DateTransformer(String dateFormatString) {
        dateFormat = new SimpleDateFormat(dateFormatString);
    }

    @Override
    public String transform(String value) {
        try {
            Date date = uk2DateFormat.parse(value);
            return dateFormat.format(date);
        } catch (Exception ignored) {

        }
        return null;
    }

    @Override
    public boolean isValid(String value) {
        return transform(value) != null;
    }
}
