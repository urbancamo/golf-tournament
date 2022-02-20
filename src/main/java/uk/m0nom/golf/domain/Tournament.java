package uk.m0nom.golf.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * From each data source, you must persist at least this base set of data:
 * an external ID for the tournament;
 * start and finish dates for the tournament;
 * the golf course name;
 * the country hosting the tournament;
 * the source of the tournament data;
 * the number of rounds to be played.
 */
@Getter
@Setter
@AllArgsConstructor
public class Tournament {
    public static final String ID_KEYNAME = "id";
    public static final String SOURCE_KEYNAME = "source";

    Integer source;
    Map<String, String> data;

    public Tournament(Integer source) {
        setSource(source);
        data = new HashMap<>();
    }

    public Tournament(Map<String, String> data) {
        setData(data);
        setSource(Integer.valueOf(data.get("source")));
    }

    public String getId() {
        return data.get("id");
    }

    public Set<String> keySet() {
        return data.keySet();
    }

    public String get(String key) {
        return data.get(key);
    }

    public void put(String key, String value) {
        data.put(key, value);
    }
}
