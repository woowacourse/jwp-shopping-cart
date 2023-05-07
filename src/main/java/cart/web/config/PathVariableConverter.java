package cart.web.config;

import cart.web.dto.request.PathVariableId;
import org.springframework.core.convert.converter.Converter;

public class PathVariableConverter implements Converter<String, PathVariableId> {
    @Override
    public PathVariableId convert(final String value) {
        return PathVariableId.valueOf(value);
    }
}
