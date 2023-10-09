package com.roxyfy.model;

import java.util.Map;

/**
 * Represents data with an unknown structure. Used for parsing purposes.
 */
public interface XmlDynamicDto {

    Map<String, Object> getFields();
}
