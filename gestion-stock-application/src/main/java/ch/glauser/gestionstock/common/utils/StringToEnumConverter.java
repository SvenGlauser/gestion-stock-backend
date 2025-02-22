package ch.glauser.gestionstock.common.utils;

import ch.glauser.gestionstock.common.pagination.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

@Configuration
public class StringToEnumConverter implements Converter<String, Filter.Type> {
    @Override
    public Filter.Type convert(String source) {
        return Filter.Type.valueOf(source.toUpperCase());
    }
}
