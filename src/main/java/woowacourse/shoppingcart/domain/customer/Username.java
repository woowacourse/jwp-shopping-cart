package woowacourse.shoppingcart.domain.customer;

import woowacourse.exception.InputFormatException;
import woowacourse.exception.dto.ErrorResponse;

public class Username {
    private final String value;

    public Username(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        if(!(1 <= value.length() && value.length() <=10)){
            throw new InputFormatException("유저명 형식이 맞지 않습니다", ErrorResponse.INVALID_USERNAME);
        }
    }

    public String getValue() {
        return value;
    }
}
