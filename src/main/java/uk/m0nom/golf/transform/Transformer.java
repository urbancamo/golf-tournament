package uk.m0nom.golf.transform;

public interface Transformer {
    String transform(String value);
    boolean isValid(String value);
}
