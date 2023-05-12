package cart.dto.response;

import cart.domain.CartProduct;
import cart.domain.Product;

public class ResponseCartProductDto {

    private final Long id;
    private final String name;
    private final Integer price;
    private final String image;

    private ResponseCartProductDto(final Long id, final String name, final Integer price, final String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public static ResponseCartProductDto of(final CartProduct cartProduct) {
        final Product product = cartProduct.getProduct();
        return new ResponseCartProductDto(
                cartProduct.getCartId(),
                product.getName(),
                product.getPrice(),
                product.getImage()
        );
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

    @Override
    public String toString() {
        return "ResponseCartProductDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", image='" + image + '\'' +
                '}';
    }
}
