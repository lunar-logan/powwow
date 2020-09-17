package io.powwow.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ValidationDTO implements Serializable {
    private boolean success = true;
    private List<String> reasons;

    public static class Builder {
        private final List<String> reasons = new ArrayList<>();

        public Builder reason(String reason) {
            this.reasons.add(reason);
            return this;
        }

        public ValidationDTO build() {
            return new ValidationDTO(reasons.isEmpty(), reasons);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
