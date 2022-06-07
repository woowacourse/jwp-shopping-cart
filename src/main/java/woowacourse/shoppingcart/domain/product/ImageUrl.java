package woowacourse.shoppingcart.domain.product;

import woowacourse.shoppingcart.exception.InvalidFormException;

public class ImageUrl {

    private final String value;

    public ImageUrl(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        if (!value.startsWith("http")) {
            throw InvalidFormException.fromName("ImageUrl");
        }
    }

    public String getValue() {
        return value;
    }
}
