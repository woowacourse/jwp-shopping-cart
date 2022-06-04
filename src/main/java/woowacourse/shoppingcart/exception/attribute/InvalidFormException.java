package woowacourse.shoppingcart.exception.attribute;

public class InvalidFormException extends InvalidPropertyException {

    private static final String ERROR_MESSAGE = "%s의 형식이 올바르지 않습니다.";

    private InvalidFormException(String property) {
        super(String.format(ERROR_MESSAGE, property));
    }

    public static InvalidFormException fromName(String propertyName) {
        return new InvalidFormException(propertyName);
    }
}
