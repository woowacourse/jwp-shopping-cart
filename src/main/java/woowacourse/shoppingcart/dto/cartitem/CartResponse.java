package woowacourse.shoppingcart.dto.cartitem;

import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@ToString
public class CartResponse {

    private long productId;
    private String thumbnailUrl;
    private String name;
    private int price;
    private long quantity;
    private long count;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CartResponse that = (CartResponse) o;
        return productId == that.productId && price == that.price && quantity == that.quantity && count == that.count
                && Objects.equals(thumbnailUrl, that.thumbnailUrl) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, thumbnailUrl, name, price, quantity, count);
    }
}
