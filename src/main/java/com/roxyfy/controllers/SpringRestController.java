package com.roxyfy.controllers;

import com.roxyfy.model.DealDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

@RestController
public class SpringRestController {

    // the same URI
    @GetMapping(
            value = "/test",
            produces = APPLICATION_JSON_VALUE,
            consumes = APPLICATION_JSON_VALUE
    )
    public DealDto produceJson() {
        DealDto result = new DealDto(oneMethodForEverything());
        return result;
    }

    // the same URI
    @GetMapping(
            value = "/test",
            produces = APPLICATION_XML_VALUE,
            consumes = APPLICATION_XML_VALUE
    )
    public DealDto produceXml() {
        DealDto result = new DealDto(Map.of("result", "calculation"));
        return result;
    }

    // the same URI
    @GetMapping(
            value = "/test",
            produces = {APPLICATION_OCTET_STREAM_VALUE},
            consumes = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE}
    )
    public byte[] produceBytes() {
        DealDto result = new DealDto(oneMethodForEverything());
        return result.toString().getBytes();
    }

    private Map<String, Object> oneMethodForEverything() {
        return Map.of("result", "calculation");
    }
}
