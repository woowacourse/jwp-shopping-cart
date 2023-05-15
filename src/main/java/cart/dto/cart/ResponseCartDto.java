package cart.dto.cart;

import cart.domain.product.Product;

public class ResponseCartDto {

    private final Long productId;
    private final String productImage;
    private final String productName;
    private final int productPrice;
    private final int quantity;

    private ResponseCartDto(final Long productId, final String productImage, final String productName,
                            final int productPrice, final int quantity) {
        this.productId = productId;
        this.productImage = productImage;
        this.productName = productName;
        this.productPrice = productPrice;
        this.quantity = quantity;
    }

    public static ResponseCartDto of(final Product product, final int quantity) {
        return new ResponseCartDto(product.getId(), product.getImage(), product.getName().getValue(),
                product.getPrice().getValue(), quantity);
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductImage() {
        return productImage;
    }

    public String getProductName() {
        return productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public int getQuantity() {
        return quantity;
    }
}
