package cart.dto.cart;

import cart.domain.product.ImgUrl;
import cart.domain.product.Name;
import cart.domain.product.Price;
import cart.entity.ProductEntity;

public class CartItemDto {

    private final Long id;
    private final Name name;
    private final ImgUrl imgUrl;
    private final Price price;

    private CartItemDto(Long id, String name, String imgUrl, int price) {
        this.id = id;
        this.name = new Name(name);
        this.imgUrl = new ImgUrl(imgUrl);
        this.price = new Price(price);
    }

    public static CartItemDto of(Long id, String name, String imgUrl, int price) {
        return new CartItemDto(id, name, imgUrl, price);
    }

    public static CartItemDto fromProductEntity(ProductEntity entity) {
        return new CartItemDto(entity.getId(), entity.getName(), entity.getImgUrl(), entity.getPrice());
    }

    public String getName() {
        return name.getName();
    }

    public String getImgUrl() {
        return imgUrl.getImgUrl();
    }

    public int getPrice() {
        return price.getPrice();
    }

    public Long getId() {
        return id;
    }
}
