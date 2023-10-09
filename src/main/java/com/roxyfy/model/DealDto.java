package com.roxyfy.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DealDto implements Input {

    @JsonIgnore
    private Map<String, Object> fields = new HashMap<>();

    @JsonAnySetter
    public void setField(String name, Object value) {
        fields.put(name, value);
    }

    @JsonAnyGetter
    public Map<String, Object> getFields() {
        return fields;
    }
}
