package cart.dto;

import cart.entity.Product;

public class ProductModifyRequestDto {

    private String name;
    private int price;
    private String imageUrl;

    private ProductModifyRequestDto() {
    }

    public ProductModifyRequestDto(String name, int price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
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

    public Product toEntity() {
        return new Product.Builder()
                .name(name)
                .price(price)
                .imageUrl(imageUrl)
                .build();
    }

}
