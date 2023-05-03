package cart.dto;

import cart.entity.Product;
import cart.vo.Name;
import cart.vo.Price;
import cart.vo.Url;

public class ProductDto {

    private final String name;
    private final Integer price;
    private final String imageUrl;

    public ProductDto(String name, Integer price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Product toEntity() {
        return new Product.Builder()
                .name(Name.from(name))
                .price(Price.from(price))
                .imageUrl(Url.from(imageUrl))
                .build();
    }

}
