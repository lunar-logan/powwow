package io.powwow.server.model.definition;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Properties;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SubscriberDTO implements Serializable {
    @NotBlank
    private String name;

    @NotBlank
    private String callbackUrl;

    private Properties properties;

    @NotBlank
    private String state;
}
