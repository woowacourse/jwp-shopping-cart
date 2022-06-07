package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Product;

public class CartResponseElement {

    private Long id;
    private String name;
    private int price;
    private String imageUrl;
    private int quantity;
    private boolean check;

    public CartResponseElement() {
    }

    public CartResponseElement(Product product, boolean checked, int quantity) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.imageUrl = product.getImageUrl();
        this.quantity = quantity;
        this.check = checked;
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

    public boolean getCheck() {
        return check;
    }

    @Override
    public String toString() {
        return "CartResponseElement{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", imageUrl='" + imageUrl + '\'' +
                ", quantity=" + quantity +
                ", check=" + check +
                '}' + "\n";
    }
}
