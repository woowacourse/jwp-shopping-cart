package cart.controller.dto;

import cart.service.dto.ProductDto;

public class ProductResponse {

    private final Long id;
    private final String name;
    private final Integer price;
    private final String imageUrl;

    public ProductResponse(final ProductDto productDto) {
        this.id = productDto.getId();
        this.name = productDto.getName();
        this.price = productDto.getPrice();
        this.imageUrl = productDto.getImageUrl();
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

    public String getImageUrl() {
        return imageUrl;
    }

}
