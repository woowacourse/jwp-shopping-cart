package cart.web.config;

public class PathVariableId {
    private static final int MIN_PATH_VARIABLE_ID = 1;

    private final Long id;

    public PathVariableId(final Long id) {
        validate(id);
        this.id = id;
    }

    private void validate(final Long id) {
        if (id < MIN_PATH_VARIABLE_ID) {
            throw new IllegalArgumentException();
        }
    }

    public static PathVariableId valueOf(final String value) {
        final Long longValue = parseId(value);

        return new PathVariableId(longValue);
    }

    private static Long parseId(final String value) {
        try {
            return Long.parseLong(value);
        } catch (final NumberFormatException exception) {
            throw new IllegalArgumentException("경로에 포함된 Id는 숫자만 가능합니다.");
        }
    }

    public Long getId() {
        return id;
    }
}
