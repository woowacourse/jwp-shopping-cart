package woowacourse.shoppingcart.exception.badrequest;

import lombok.Getter;

@Getter
public class DuplicateDomainException extends BadRequestException {

    private final String field;

    protected DuplicateDomainException(String field, String message) {
        super(message);
        this.field = field;
    }
}
