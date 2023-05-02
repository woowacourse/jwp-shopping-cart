package cart.domain.product;

import java.math.BigDecimal;
import java.util.Objects;

public class ProductPrice {
    private static final BigDecimal MINIMUM_UNIT = BigDecimal.valueOf(100);

    private final BigDecimal price;

    private ProductPrice(BigDecimal price) {
        validate(price);
        this.price = price;
    }

    public static ProductPrice from(int price) {
        BigDecimal productPrice = BigDecimal.valueOf(price);

        return new ProductPrice(productPrice);
    }

    private void validate(BigDecimal price) {
        if (isLessThanMinimum(price)) {
            throw new IllegalArgumentException("삼품 가격은 100원 이상이어야 합니다.");
        }

        if (isNotProductPriceUnit(price)) {
            throw new IllegalArgumentException("상품 가격은 100원 단위로 구성되어야 합니다.");
        }
    }

    private boolean isNotProductPriceUnit(BigDecimal price) {
        return !price.remainder(MINIMUM_UNIT).equals(BigDecimal.ZERO);
    }

    private boolean isLessThanMinimum(BigDecimal price) {
        return price.compareTo(MINIMUM_UNIT) < 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductPrice that = (ProductPrice) o;
        return Objects.equals(getPrice(), that.getPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPrice());
    }

    public BigDecimal getPrice() {
        return price;
    }
}
