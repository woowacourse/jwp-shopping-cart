package woowacourse.shoppingcart.domain;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import woowacourse.shoppingcart.dto.Request;

public class Product {

    @NotNull(groups = Request.id.class)
    private Long id;
    @NotNull(groups = Request.allProperties.class)
    private String name;
    @Min(value = 0, groups = Request.allProperties.class)
    private Integer price;
    @NotNull(groups = Request.allProperties.class)
    private String imageUrl;

    public Product() {
    }

    public Product(final Long id, final String name, final int price, final String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Product(final String name, final int price, final String imageUrl) {
        this(null, name, price, imageUrl);
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

    public Long getId() {
        return id;
    }
}
