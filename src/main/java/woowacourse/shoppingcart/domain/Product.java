package woowacourse.shoppingcart.domain;

import javax.validation.constraints.NotNull;
import woowacourse.shoppingcart.dto.request.Request;

public class Product {
    @NotNull(groups = Request.id.class)
    private Long id;
    @NotNull(groups = Request.allProperties.class)
    private String name;
    @NotNull(groups = Request.allProperties.class)
    private int price;
    @NotNull(groups = Request.allProperties.class)
    private String thumbnail;

    public Product() {
    }

    public Product(final Long id, final String name, final int price, final String thumbnail) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.thumbnail = thumbnail;
    }

    public Product(final String name, final int price, final String thumbnail) {
        this(null, name, price, thumbnail);
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public Long getId() {
        return id;
    }
}
