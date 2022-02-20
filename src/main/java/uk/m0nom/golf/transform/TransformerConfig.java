package uk.m0nom.golf.transform;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "transformer")
@Component
@Getter
@Setter
public class TransformerConfig {
    private String dateFormat;
}
