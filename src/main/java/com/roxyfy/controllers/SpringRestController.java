package com.roxyfy.controllers;

import com.roxyfy.model.JsonDealDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

@RestController
public class SpringRestController {

    @GetMapping(
            value = "/test",
            produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE},
            consumes = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE}
    )
    public JsonDealDto produceJson() {
        JsonDealDto result = new JsonDealDto(Map.of("result", "calculation"));
        return result;
    }

    @GetMapping(
            value = "/test",
            produces = {APPLICATION_OCTET_STREAM_VALUE},
            consumes = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE}
    )
    public byte[] produceBytes() {
        JsonDealDto result = new JsonDealDto(Map.of("result", "calculation"));
        return result.toString().getBytes();
    }
}
