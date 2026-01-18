package ch.glauser.gestionstock.common.utils;

import ch.glauser.filters.automatic.AutomaticField;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

@Configuration
public class StringToEnumConverter implements Converter<String, AutomaticField.Type> {
    @Override
    public AutomaticField.Type convert(String source) {
        return AutomaticField.Type.valueOf(source.toUpperCase());
    }
}
