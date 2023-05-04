package cart.dto;

import cart.domain.ImgUrl;
import cart.domain.Name;
import cart.domain.Price;
import cart.entity.ProductEntity;

public class ProductDto {

    private final Long id;
    private final Name name;
    private final ImgUrl imgUrl;
    private final Price price;

    private ProductDto(Long id, String name, String imgUrl, int price) {
        this.id = id;
        this.name = new Name(name);
        this.imgUrl = new ImgUrl(imgUrl);
        this.price = new Price(price);
    }

    public static ProductDto fromEntity(ProductEntity entity) {
        return new ProductDto(entity.getId(), entity.getName(), entity.getImgUrl(), entity.getPrice());
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
