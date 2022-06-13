package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Product;

public class ProductDto {
    private Long id;
    private String name;
    private int price;
    private int quantity;
    private String imageURL;

    private ProductDto() {
    }

    public ProductDto(Long id, String name, int price, int quantity, String imageURL) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.imageURL = imageURL;
    }

    public static ProductDto of(Product product) {
        return new ProductDto(
            product.getId(),
            product.getName(),
            product.getPrice(),
            product.getQuantity(),
            product.getImageUrl()
        );
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

    public int getQuantity() {
        return quantity;
    }

    public String getImageURL() {
        return imageURL;
    }
}
