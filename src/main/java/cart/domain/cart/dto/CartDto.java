package cart.domain.cart.dto;

import cart.domain.cart.Cart;

public class CartDto {
    private final Long id;
    private final String name;
    private final int price;
    private final String category;
    private final String imageUrl;

    public CartDto(Long id, String name, int price, String category, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.imageUrl = imageUrl;
    }

    public CartDto(Cart cart) {
        this(
                cart.getId(),
                cart.getProduct().getName(),
                cart.getProduct().getPrice().intValue(),
                cart.getProduct().getCategory().name(),
                cart.getProduct().getImageUrl()
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

    public String getCategory() {
        return category;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
