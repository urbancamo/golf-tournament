package uk.m0nom.golf.dynamodb;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "aws.dynamodb")
@Component
@Getter
@Setter
public class DynamoDbConfig {
    private String uri;

    private String accessKeyId;

    private String secretAccessKey;
}
