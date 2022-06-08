package woowacourse.shoppingcart.dto.product;

import woowacourse.shoppingcart.domain.product.Product;

public class ProductSaveResponse {

    private long productId;
    private String name;
    private int price;
    private String image;

    public ProductSaveResponse() {
    }

    public ProductSaveResponse(long productId, String name, int price, String image) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public ProductSaveResponse(Product product) {
        this(product.getId(), product.getName(), product.getPrice(), product.getImage());
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

    public long getProductId() {
        return productId;
    }
}
