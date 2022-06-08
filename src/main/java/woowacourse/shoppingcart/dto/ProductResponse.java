package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Image;
import woowacourse.shoppingcart.domain.Product;

public class ProductResponse {

    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
    private ImageDto thumbnailImage;

    public ProductResponse() {
    }

    private ProductResponse(Long id, String name, int price, int stockQuantity, ImageDto thumbnailImage) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.thumbnailImage = thumbnailImage;
    }

    public static ProductResponse of(Product product) {
        final Image image = product.getImage();
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(),
                product.getStockQuantity(), new ImageDto(image.getUrl(), image.getAlt()));
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

    public int getStockQuantity() {
        return stockQuantity;
    }

    public ImageDto getThumbnailImage() {
        return thumbnailImage;
    }
}
