package cart.dto;

import cart.domain.ImgUrl;
import cart.domain.Name;
import cart.domain.Price;
import cart.entity.ProductEntity;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductDto that = (ProductDto) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(imgUrl, that.imgUrl) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, imgUrl, price);
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "id=" + id +
                ", name=" + name +
                ", imgUrl=" + imgUrl +
                ", price=" + price +
                '}';
    }
}
