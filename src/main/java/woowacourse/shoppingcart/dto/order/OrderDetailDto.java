package woowacourse.shoppingcart.dto.order;

import woowacourse.shoppingcart.domain.cart.Cart;
import woowacourse.shoppingcart.domain.order.OrderDetail;
import woowacourse.shoppingcart.domain.product.Product;

public class OrderDetailDto {
    private Long productId;
    private String name;
    private int quantity;
    private int price;
    private String image;

    public OrderDetailDto(Long productId, String name, int quantity, int price, String image) {
        this.productId = productId;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.image = image;
    }

    public OrderDetailDto(Product product, OrderDetail orderDetail) {
        this.productId = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.image = product.getImage();
        this.quantity = orderDetail.getQuantity();
    }

    public OrderDetailDto(Cart cart, Product product) {
        this.productId = product.getId();
        this.name = product.getName();
        this.quantity = cart.getQuantity();
        this.price = product.getPrice();
        this.image = product.getImage();
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }
}
