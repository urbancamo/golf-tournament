package uk.m0nom.golf.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Transform {
    private Integer source;
    private String key;
    private String mappedKey;
    private String valueTransformer;
}
