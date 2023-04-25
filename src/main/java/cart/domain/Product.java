package cart.domain;

import lombok.Getter;

@Getter
public class Product {

    private Long id;
    private final String name;
    private final String image;
    private final int price;


    public Product(final String name, final String image, final int price) {
        this.name = name;
        this.image = image;
        this.price = price;
    }
}
