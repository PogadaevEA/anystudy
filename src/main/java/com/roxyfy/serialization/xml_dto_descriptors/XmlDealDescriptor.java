package com.roxyfy.serialization.xml_dto_descriptors;

import com.roxyfy.model.XmlDealDto;
import com.roxyfy.serialization.XmlDynamicTypeDescriptor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class XmlDealDescriptor implements XmlDynamicTypeDescriptor<XmlDealDto> {

    @Override
    public XmlDealDto createInstance(Map<String, Object> fields) {
        return new XmlDealDto(fields);
    }

    @Override
    public Class<XmlDealDto> getTypeClass() {
        return XmlDealDto.class;
    }
}
