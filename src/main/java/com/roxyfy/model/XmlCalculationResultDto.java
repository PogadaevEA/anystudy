package com.roxyfy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class XmlCalculationResultDto implements XmlDynamicDto {

    private Map<String, Object> fields;
}
