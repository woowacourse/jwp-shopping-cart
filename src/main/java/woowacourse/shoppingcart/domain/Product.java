package woowacourse.shoppingcart.domain;

import woowacourse.shoppingcart.exception.dataformat.ProductDataFormatException;

public class Product {

    private static final int MINIMUM_PRICE = 1;
    private static final int MONEY_UNIT = 10;

    private final Long id;
    private final String name;
    private final Integer price;
    private final String imageUrl;

    public Product(final Long id, final String name, final int price, final String imageUrl) {
        validatePrice(price);
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Product(final String name, final int price, final String imageUrl) {
        this(null, name, price, imageUrl);
    }

    private void validatePrice(final int price) {
        if (price < MINIMUM_PRICE) {
            throw new ProductDataFormatException("상품의 가격은 0 원 이상이어야 합니다.");
        }
        if (price % MONEY_UNIT != 0) {
            throw new ProductDataFormatException("상품의 가격은 10원 단위로 입력해주세요.");
        }
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Long getId() {
        return id;
    }
}
