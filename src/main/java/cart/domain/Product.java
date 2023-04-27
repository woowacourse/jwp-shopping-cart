package cart.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Product {

    private final Long id;
    private final String name;
    private final String imageUrl;
    private final int price;

    public Product(final Long id, final String name, final String imageUrl, final int price) {
        String strippedName = name.strip();
        String strippedImageUrl = imageUrl.strip();
        validate(strippedName, strippedImageUrl, price);
        this.id = id;
        this.name = strippedName;
        this.imageUrl = strippedImageUrl;
        this.price = price;
    }

    public Product(final String name, final String imageUrl, final int price) {
        this(null, name, imageUrl, price);
    }


    private void validate(final String name, final String imageUrl, final int price) {
        validateName(name);
        validateImageUrl(imageUrl);
        validatePrice(price);
    }

    private void validateName(String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("상품 이름은 빈 문자열일 수 없습니다");
        }
        if (name.length() < 1 || name.length() > 30) {
            throw new IllegalArgumentException("상품 이름은 최소 1, 최대 30 글자입니다");
        }
    }

    private void validateImageUrl(String imageUrl) {
        if (imageUrl.isBlank()) {
            throw new IllegalArgumentException("이미지 URL은 빈 문자열일 수 없습니다");
        }
        if (imageUrl.length() < 1 || imageUrl.length() > 1000) {
            throw new IllegalArgumentException("이미지 URL은 최소 1, 최대 1000 글자입니다");
        }
    }

    private void validatePrice(int price) {
        if (price < 0 || price > 1_000_000_000) {
            throw new IllegalArgumentException("유효한 상품 금액이 아닙니다.");
        }
    }
}
