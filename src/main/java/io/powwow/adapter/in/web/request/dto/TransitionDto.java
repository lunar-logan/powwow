package io.powwow.adapter.in.web.request.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransitionDto implements Serializable {
    private String fromState;
    private String toState;
    private String onEvent;
    private List<String> tasks;
}
