package woowacourse.shoppingcart.domain.product;

import java.util.Objects;

import woowacourse.shoppingcart.domain.product.vo.Price;
import woowacourse.shoppingcart.domain.product.vo.ThumbnailImage;

public class Product {
    private Long id;
    private String name;
    private Price price;
    private ThumbnailImage thumbnailImage;

    public Product() {
    }

    public Product(final Long id, final String name, final int price, final ThumbnailImage thumbnailImage) {
        this.id = id;
        this.name = name;
        this.price = new Price(price);
        this.thumbnailImage = thumbnailImage;

    }

    public Product(final String name, final int price, final ThumbnailImage thumbnailImage) {
        this(null, name, price, thumbnailImage);
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price.getPrice();
    }

    public String getThumbnailImageUrl() {
        return thumbnailImage.getUrl();
    }

    public String getThumbnailImageAlt() {
        return thumbnailImage.getAlt();
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Product))
            return false;
        Product product = (Product)o;
        return getId().equals(product.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
