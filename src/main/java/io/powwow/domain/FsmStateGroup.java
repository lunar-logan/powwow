package io.powwow.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FsmStateGroup {
    private List<FsmStateWrapper> states;
    private boolean isCurrent;
}
