package woowacourse.shoppingcart.domain.product;

import woowacourse.shoppingcart.exception.attribute.InvalidFormException;

public class ImageUrl {

    private String value;

    public ImageUrl(String value) {
        validateStartsWithHttp(value);
        this.value = value;
    }

    private void validateStartsWithHttp(String value) {
        if (!value.startsWith("http")) {
            throw InvalidFormException.fromName("이미지 URL");
        }
    }

    public String getValue() {
        return value;
    }
}
