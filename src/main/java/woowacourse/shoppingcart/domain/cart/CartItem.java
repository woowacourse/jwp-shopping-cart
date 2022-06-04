package woowacourse.shoppingcart.domain;

import woowacourse.shoppingcart.domain.product.Product;

public class Cart {

    private Long id;
    private Product product;

    public Cart() {
    }

    public Cart(Long id, Product product) {
        this.id = id;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return product.getId();
    }

    public String getName() {
        return product.getName();
    }

    public int getPrice() {
        return product.getPrice();
    }

    public String getImageUrl() {
        return product.getImageUrl();
    }
}
