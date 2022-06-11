package woowacourse.shoppingcart.exception.badrequest;

public class DuplicateDomainException extends BadRequestException {

    private final String field;

    protected DuplicateDomainException(String field, String message) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
