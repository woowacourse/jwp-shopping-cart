package cart.dto;

import cart.entity.Cart;

public class CartDto {
    private final int id;
    private final String name;
    private final int price;
    private final String image;

    private CartDto(int id, String name, int price, String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public static CartDto from(Cart cart) {
        return new CartDto(cart.getId(), cart.getName(), cart.getPrice(), cart.getImage());
    }

    public int getId() {
        return id;
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
}
