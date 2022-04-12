package shoppingcart.dto;

public class CartDto {

    private Long cartId;
    private Long productId;
    private String name;
    private int price;
    private String imageUrl;

    public CartDto() {
    }

    public CartDto(final Long cartId, final ProductDto product) {
        this(cartId, product.getProductId(), product.getName(), product.getPrice(), product.getImageUrl());
    }

    public CartDto(final Long cartId, final Long productId, final String name, final int price, final String imageUrl) {
        this.cartId = cartId;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Long getCartId() {
        return cartId;
    }

    public Long getProductId() {
        return productId;
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
