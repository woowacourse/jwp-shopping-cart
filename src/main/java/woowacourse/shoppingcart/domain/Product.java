package woowacourse.shoppingcart.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class Product {

    private Long id;
    private String name;
    private Integer price;
    private String imageUrl;
    private Long quantity;

    public Product(final String name, final int price, final String imageUrl, final long quantity) {
        this(null, name, price, imageUrl, quantity);
    }
}
