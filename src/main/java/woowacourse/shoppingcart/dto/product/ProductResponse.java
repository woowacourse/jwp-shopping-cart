package woowacourse.shoppingcart.dto.product;

import woowacourse.shoppingcart.domain.Product;

public class ProductResponse {

    private Long id;
    private String name;
    private int price;
    private String imageUrl;
    private int stock;

    private ProductResponse() {
    }

    public ProductResponse(Long id, String name, int price, String imageUrl, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.stock = stock;
    }

    public ProductResponse(Product product) {
        this(product.getId(), product.getName(), product.getPrice(), product.getImageUrl(), product.getStock());
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

    public int getStock() {
        return stock;
    }
}
