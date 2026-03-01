package ch.glauser.gestionstock.common.utils;

import ch.glauser.filters.automatic.AutomaticSearchField;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

@Configuration
public class StringToEnumConverter implements Converter<String, AutomaticSearchField.Type> {
    @Override
    public AutomaticSearchField.Type convert(String source) {
        return AutomaticSearchField.Type.valueOf(source.toUpperCase());
    }
}
