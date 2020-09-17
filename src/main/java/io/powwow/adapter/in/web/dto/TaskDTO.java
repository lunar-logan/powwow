package io.powwow.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.powwow.model.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskDTO implements Serializable {

    // Name of the task
    private String id;

    private Map<TaskStatus, List<String>> statusEventMap;
}
