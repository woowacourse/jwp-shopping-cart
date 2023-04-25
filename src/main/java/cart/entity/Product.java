package cart.entity;

import cart.dto.ProductRequestDto;

public class Product {

    private final Long id;
    private final String name;
    private final String imageUrl;
    private final int price;

    public Product(final Long id, final String name, final String imageUrl, final int price) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public Product(final String name, final String imageUrl, final int price) {
        this.id = null;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public Product(final ProductRequestDto productRequestDto) {
        this.id = null;
        this.name = productRequestDto.getName();
        this.imageUrl = productRequestDto.getImageUrl();
        this.price = productRequestDto.getPrice();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getPrice() {
        return price;
    }
}
