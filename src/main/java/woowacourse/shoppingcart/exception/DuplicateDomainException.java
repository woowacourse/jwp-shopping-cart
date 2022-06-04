package woowacourse.shoppingcart.exception;

public class DuplicateDomainException extends RuntimeException {

    private final String field;

    protected DuplicateDomainException(String field, String message) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
