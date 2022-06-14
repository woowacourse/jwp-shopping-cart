package woowacourse.shoppingcart.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import woowacourse.shoppingcart.domain.Product;

public class OrderedProduct {

    private Long productId;
    private int quantity;
    private int price;
    private String name;
    @JsonProperty("thumbnailImage")
    private ImageDto thumbnailImage;

    public OrderedProduct() {
    }

    public OrderedProduct(Long productId, int quantity, int price, String name, ImageDto thumbnailImage) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.name = name;
        this.thumbnailImage = thumbnailImage;
    }

    public static OrderedProduct of(Product product, int quantity) {
        return new OrderedProduct(product.getId(), quantity,
                product.getPrice(), product.getName(), ImageDto.of(product.getImage()));
    }

    public Long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public ImageDto getThumbnailImage() {
        return thumbnailImage;
    }
}
