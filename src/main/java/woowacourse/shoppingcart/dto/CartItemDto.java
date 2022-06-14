package woowacourse.shoppingcart.dto;

import java.util.List;
import java.util.stream.Collectors;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Image;

public class CartItemDto {

    private Long id;
    private Long productId;
    private String name;
    private int price;
    private int quantity;
    private ImageDto thumbnailImage;

    public CartItemDto() {
    }

    private CartItemDto(Long id, Long productId, String name, int price, int quantity, ImageDto thumbnailImage) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.thumbnailImage = thumbnailImage;
    }

    public static CartItemDto of(CartItem cartItem) {
        final Image image = cartItem.getProduct().getImage();
        return new CartItemDto(cartItem.getId(),
                cartItem.getProduct().getId(),
                cartItem.getProduct().getName(),
                cartItem.getProduct().getPrice(),
                cartItem.getQuantity(),
                new ImageDto(image.getUrl(), image.getAlt()));
    }

    public static List<CartItemDto> of(final List<CartItem> cartItems) {
        return cartItems.stream()
                .map(cart -> new CartItemDto(cart.getId(),
                        cart.getProduct().getId(),
                        cart.getProduct().getName(),
                        cart.getProduct().getPrice(),
                        cart.getQuantity(),
                        ImageDto.of(cart.getProduct().getImage())))
                .collect(Collectors.toUnmodifiableList());
    }

    public Long getId() {
        return id;
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

    public int getQuantity() {
        return quantity;
    }

    public ImageDto getThumbnailImage() {
        return thumbnailImage;
    }
}
