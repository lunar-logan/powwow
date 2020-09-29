package io.powwow.server.model.ui;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TableModel implements Serializable {
    private List<String> headers;
    private List<List<?>> rows;
}
