package woowacourse.shoppingcart.exception.badrequest;

import lombok.Getter;

@Getter
public class InvalidFieldException extends BadRequestException {

    private final String field;

    public InvalidFieldException(String field, String message) {
        super(message);
        this.field = field;
    }
}
