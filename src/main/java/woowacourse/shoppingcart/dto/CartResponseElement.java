package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Product;

public class CartResponseElement {

    private Long id;
    private Long productId;
    private String name;
    private int price;
    private String imageUrl;
    private int quantity;
    private boolean checked;

    public CartResponseElement() {
    }

    public CartResponseElement(Long id, Product product, boolean checked, int quantity) {
        this.id = id;
        this.name = product.getName();
        this.price = product.getPrice();
        this.productId = product.getId();
        this.imageUrl = product.getImageUrl();
        this.quantity = quantity;
        this.checked = checked;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean getChecked() {
        return checked;
    }

    @Override
    public String toString() {
        return "CartResponseElement{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", imageUrl='" + imageUrl + '\'' +
                ", quantity=" + quantity +
                ", check=" + checked +
                '}' + "\n";
    }
}
