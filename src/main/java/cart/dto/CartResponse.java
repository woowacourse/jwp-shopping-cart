package cart.dto;

import cart.dao.dto.CartProductResultMap;

public class CartResponse {

    private final Long id;
    private final Long productId;
    private final String name;
    private final int price;
    private final String imgUrl;

    private final int count;

    public CartResponse(final Long id, final Long productId, final String name, final int price, final String imgUrl, final int count) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
        this.count = count;
    }

    public CartResponse(final CartProductResultMap cartProductResultMap) {

        this(cartProductResultMap.getId(),
                cartProductResultMap.getProductId(),
                cartProductResultMap.getName(),
                cartProductResultMap.getPrice(),
                cartProductResultMap.getImgUrl(),
                cartProductResultMap.getCount());
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public int getCount() {
        return count;
    }
}
