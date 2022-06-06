package woowacourse.shoppingcart.domain;

import woowacourse.shoppingcart.domain.product.Product;

public class Cart {

    private Long id;
    private Long productId;
    private String name;
    private int price;
    private String image;

    public Cart() {
    }

    public Cart(final Long id, final Product product) {
        this(id, product.getId(), product.getName(), product.getPrice(), product.getImage());
    }

    public Cart(final Long id, final Long productId, final String name, final int price, final String image) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }
}
