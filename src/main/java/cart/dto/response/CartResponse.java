package cart.dto.response;

import cart.domain.Product;

public class CartResponse {

    private Long id;
    private String name;
    private Integer price;
    private String image;

    public CartResponse(final Long id, final String name, final Integer price, final String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public static CartResponse from(final Product product) {
        return new CartResponse(product.getId(), product.getName(), product.getPrice(), product.getImage());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }
}
