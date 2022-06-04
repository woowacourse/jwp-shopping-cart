package woowacourse.shoppingcart.exception.attribute;

public class NegativeNumberException extends InvalidPropertyException {

    private static final String ERROR_MESSAGE = "%s 는 음수가 될 수 없습니다.";

    public NegativeNumberException(String message) {
        super(message);
    }

    public static NegativeNumberException fromName(String name) {
        return new NegativeNumberException(String.format(ERROR_MESSAGE, name));
    }
}
