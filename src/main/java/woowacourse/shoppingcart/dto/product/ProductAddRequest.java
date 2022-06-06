package woowacourse.shoppingcart.dto.product;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import woowacourse.shoppingcart.domain.product.Product;

public class ProductAddRequest {

    @NotBlank
    private String productName;

    @NotNull
    private int price;

    @NotNull
    private int stock;

    @NotNull
    private String imageUrl;

    private ProductAddRequest() {
    }

    public ProductAddRequest(final String productName, final int price, final int stock, final String imageUrl) {
        this.productName = productName;
        this.price = price;
        this.stock = stock;
        this.imageUrl = imageUrl;
    }

    public Product toProduct() {
        return Product.builder()
                .productName(productName)
                .price(price)
                .stock(stock)
                .imageUrl(imageUrl)
                .build();
    }

    public String getProductName() {
        return productName;
    }

    public int getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
