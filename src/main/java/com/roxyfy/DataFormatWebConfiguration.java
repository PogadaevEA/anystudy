package com.roxyfy;

import com.roxyfy.serialization.XmlDynamicTypeDescriptor;
import com.roxyfy.serialization.marshalling.XmlDynamicDtoMarshaller;
import com.roxyfy.serialization.unmarshalling.XmlDynamicDtoUnmarshaller;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class DataFormatWebConfiguration implements WebMvcConfigurer {

    private final List<XmlDynamicTypeDescriptor<?>> typeDescriptors;

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        typeDescriptors.stream()
                .map(descriptor -> new MarshallingHttpMessageConverter(new XmlDynamicDtoMarshaller<>(descriptor), new XmlDynamicDtoUnmarshaller<>(descriptor)))
                .forEach(converters::add);
    }
}
