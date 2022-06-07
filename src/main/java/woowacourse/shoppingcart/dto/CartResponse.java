package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Cart;

public class CartResponse {

    private Long id;
    private String name;
    private int price;
    private String imageUrl;
    private int quantity;
    private boolean checked;

    public CartResponse() {
    }

    public CartResponse(Long id, String name, int price, String imageUrl, int quantity, boolean checked) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
        this.checked = checked;
    }

    public static CartResponse from(Cart cart) {
        return new CartResponse(cart.getId(), cart.getName(), cart.getPrice(), cart.getImageUrl(), cart.getQuantity(),
                cart.isChecked());
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

    public int getQuantity() {
        return quantity;
    }

    public boolean isChecked() {
        return checked;
    }

    @Override
    public String toString() {
        return "CartResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", imageUrl='" + imageUrl + '\'' +
                ", quantity=" + quantity +
                ", checked=" + checked +
                '}';
    }
}
