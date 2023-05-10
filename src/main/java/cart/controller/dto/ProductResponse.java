package cart.controller.dto;

import cart.service.dto.ProductInfoDto;

public class ProductResponse {

    private final long id;
    private final String imgUrl;
    private final String name;
    private final int price;

    public ProductResponse(final long id, final String imgUrl, final String name, final int price) {
        this.id = id;
        this.imgUrl = imgUrl;
        this.name = name;
        this.price = price;
    }

    public static ProductResponse fromDto(final ProductInfoDto productInfoDto) {
        return new ProductResponse(productInfoDto.getId(), productInfoDto.getImgUrl(), productInfoDto.getName(),
                productInfoDto.getPrice());
    }

    public long getId() {
        return id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
