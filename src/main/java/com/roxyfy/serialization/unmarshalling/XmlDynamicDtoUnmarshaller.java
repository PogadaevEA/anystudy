package com.roxyfy.serialization.unmarshalling;

import com.roxyfy.model.XmlDynamicDto;
import com.roxyfy.serialization.XmlDynamicTypeDescriptor;
import lombok.RequiredArgsConstructor;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.UnmarshallingFailureException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;

/**
 * Converts any XML to the {@link XmlDynamicDto} described by {@link XmlDynamicTypeDescriptor}.
 *
 * @param <TYPE> type of supported instances
 */
@SuppressWarnings("NullableProblems")
@RequiredArgsConstructor
public class XmlDynamicDtoUnmarshaller<TYPE extends XmlDynamicDto> implements Unmarshaller {

    private final XmlDynamicTypeDescriptor<TYPE> typeDescriptor;

    @Override
    public boolean supports(Class<?> clazz) {
        return typeDescriptor.getTypeClass().isAssignableFrom(clazz);
    }

    @Override
    public Object unmarshal(Source source) throws IOException {
        if (source instanceof StreamSource streamSource && streamSource.getInputStream() != null) {
            return unmarshalInputSource(new InputSource(streamSource.getInputStream()));
        }

        if (source instanceof StreamSource streamSource && streamSource.getReader() != null) {
            return unmarshalInputSource(new InputSource(streamSource.getReader()));
        }

        if (source instanceof SAXSource saxSource) {
            return unmarshalInputSource(saxSource.getInputSource());
        }

        if (source instanceof InputSource inputSource) {
            return unmarshalInputSource(inputSource);
        }

        throw new IllegalArgumentException("Unsupported source type: " + source.getClass().getName());
    }

    private Object unmarshalInputSource(InputSource source) throws IOException {
        try {
            var parser = SAXParserFactory.newInstance().newSAXParser();
            var eventHandler = new SaxEventHandler();

            parser.parse(source, eventHandler);

            return typeDescriptor.createInstance(eventHandler.getResult());
        } catch (ParserConfigurationException | SAXException e) {
            throw new UnmarshallingFailureException(e.getMessage(), e);
        }
    }
}
