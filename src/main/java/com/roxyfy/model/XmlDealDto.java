package com.roxyfy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class XmlDealDto implements XmlDynamicDto, Input {

    private Map<String, Object> fields;
}
