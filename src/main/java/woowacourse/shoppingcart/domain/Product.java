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

    public Product(final String name, final int price, final String imageUrl) {
        this(null, name, price, imageUrl);
    }
}
