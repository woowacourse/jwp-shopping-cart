package woowacourse.shoppingcart.domain;

import static lombok.EqualsAndHashCode.*;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
public class Product {

    @Include
    private Long id;
    private ProductName name;
    private Price price;
    private ImageUrl imageUrl;

    public Product(String name, int price, String imageUrl) {
        this(null, name, price, imageUrl);
    }

    public Product(Long id, String name, int price, String imageUrl) {
        this.id = id;
        this.name = new ProductName(name);
        this.price = new Price(price);
        this.imageUrl = new ImageUrl(imageUrl);
    }

    public boolean isSameId(Long id) {
        return this.id.equals(id);
    }

    public int multiplyPrice(int quantity) {
        return price.multiply(quantity);
    }

    public String getName() {
        return name.getValue();
    }

    public int getPrice() {
        return price.getValue();
    }

    public String  getImageUrl() {
        return imageUrl.getValue();
    }
}
