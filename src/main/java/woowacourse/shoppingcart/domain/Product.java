package woowacourse.shoppingcart.domain;

import javax.validation.constraints.NotNull;
import woowacourse.shoppingcart.dto.request.Request;

public class Product {
    @NotNull(groups = Request.id.class)
    private Long id;
    @NotNull(groups = Request.allProperties.class)
    private String name;
    @NotNull(groups = Request.allProperties.class)
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }
}
