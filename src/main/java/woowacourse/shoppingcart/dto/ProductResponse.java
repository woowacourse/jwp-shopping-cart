package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Product;

public class ProductResponse {

    private Long id;
    private String name;
    private Integer price;
    private String thumbnail;
    private boolean isStored;

    public ProductResponse() {
    }

    public ProductResponse(Product product, boolean isStored) {
        this(product.getId(), product.getName(), product.getPrice(), product.getImageUrl(), isStored);
    }

    public ProductResponse(Long id, String name, Integer price, String thumbnail, boolean isStored) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.thumbnail = thumbnail;
        this.isStored = isStored;
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

    public String getThumbnail() {
        return thumbnail;
    }

    public boolean getIsStored() {
        return isStored;
    }
}
