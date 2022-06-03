package woowacourse.shoppingcart.exception.attribute;

public class InvalidLengthException extends InvalidPropertyException {

    private static final String ERROR_MESSAGE = "%s의 길이가 유효하지 않습니다.";

    private InvalidLengthException(String property) {
        super(String.format(ERROR_MESSAGE, property));
    }

    public static InvalidLengthException fromName(String propertyName) {
        return new InvalidLengthException(propertyName);
    }
}
