package woowacourse.shoppingcart.exception;

public class DuplicateCustomerException extends RuntimeException {

    private final String field;

    public DuplicateCustomerException(String field, String message) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
