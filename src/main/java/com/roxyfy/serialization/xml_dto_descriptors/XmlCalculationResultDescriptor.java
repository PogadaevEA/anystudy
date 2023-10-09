package com.roxyfy.serialization.xml_dto_descriptors;

import com.roxyfy.model.XmlCalculationResultDto;
import com.roxyfy.serialization.XmlDynamicTypeDescriptor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class XmlCalculationResultDescriptor implements XmlDynamicTypeDescriptor<XmlCalculationResultDto> {

    @Override
    public XmlCalculationResultDto createInstance(Map<String, Object> fields) {
        return new XmlCalculationResultDto(fields);
    }

    @Override
    public Class<XmlCalculationResultDto> getTypeClass() {
        return XmlCalculationResultDto.class;
    }
}
