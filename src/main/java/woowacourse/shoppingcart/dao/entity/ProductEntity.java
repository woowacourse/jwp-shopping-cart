package woowacourse.shoppingcart.dao.entity;

import woowacourse.shoppingcart.domain.product.Product;

public class ProductEntity {

    private final Long id;
    private final String name;
    private final int price;
    private final String imageUrl;

    public ProductEntity(Long id, String name, int price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public ProductEntity(String name, int price, String imageUrl) {
        this(null, name, price, imageUrl);
    }

    public static ProductEntity from(Product product) {
        return new ProductEntity(
                product.getId(),
                product.getName().getValue(),
                product.getPrice().getValue(),
                product.getImageUrl().getValue()
        );
    }

    public Product toProduct() {
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
