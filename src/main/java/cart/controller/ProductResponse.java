package cart.controller;

public class ProductResponse {
    Long id;
    String name;
    Integer price;
    String imagePath;

    public ProductResponse(Long id, String name, Integer price, String imagePath) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imagePath = imagePath;
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

    public String getImagePath() {
        return imagePath;
    }
}
