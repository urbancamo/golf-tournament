package uk.m0nom.golf.transform;

import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Converts an Epoch value into a standard dd/mm/yyyy internal representation
 */
public class EpochTransformer implements Transformer {
    private final DateFormat dateFormat;

    public EpochTransformer(String dateFormatString) {
        dateFormat = new SimpleDateFormat(dateFormatString);
    }

    /**
     * Converts an epoch value into the internal date representation
     * @param value string representation of the long value
     * @return internal date representation of value, null if not a valid epoch
     */
    @Override
    public String transform(String value) {
        try {
            Long milliseconds = Long.valueOf(value);
            if (milliseconds > 0L) {
                Date date = new Date(milliseconds);
                if (date != null) {
                    return dateFormat.format(date);
                }
            }
        } catch (NumberFormatException ignored) {
        }
        return null;
    }

    @Override
    public boolean isValid(String value) {
        return transform(value) != null;
    }
}
