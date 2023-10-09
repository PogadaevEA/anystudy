package com.roxyfy.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonCalculationResultDto {

    @JsonIgnore
    private Map<String, Object> fields;

    @JsonAnySetter
    public void setField(String name, Object value) {
        fields.put(name, value);
    }

    @JsonAnyGetter
    public Map<String, Object> getFields() {
        return fields;
    }
}
