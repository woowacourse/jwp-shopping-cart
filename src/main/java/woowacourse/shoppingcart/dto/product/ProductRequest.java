package woowacourse.shoppingcart.dto.product;

import woowacourse.shoppingcart.domain.Product;

public class ProductRequest {
    private Long id;
    private String name;
    private int price;
    private String imageUrl;

    public ProductRequest() {
    }

    public ProductRequest(Long id, String name, int price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Product toEntity() {
        return new Product(id, name, price, imageUrl);
    }

    public Long getId() {
        return id;
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
}
