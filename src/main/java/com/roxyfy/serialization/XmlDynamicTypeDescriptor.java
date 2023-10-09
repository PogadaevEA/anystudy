package com.roxyfy.serialization;


import com.roxyfy.model.XmlDynamicDto;

import java.util.Map;

/**
 *
 * @param <TYPE> described type
 */
public interface XmlDynamicTypeDescriptor<TYPE extends XmlDynamicDto> {

    TYPE createInstance(Map<String, Object> fields);

    Class<TYPE> getTypeClass();
}
