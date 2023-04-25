package cart.dto;

public class ProductResponse {
    private final Long id;
    private final String name;
    private final Integer price;
    private final String imgUrl;

    public ProductResponse(Long id, String name, Integer price, String imgUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
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

    public String getImgUrl() {
        return imgUrl;
    }
}
