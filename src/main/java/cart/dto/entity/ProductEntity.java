package cart.dto.entity;

public class ProductEntity {
    private int id;
    private String name;
    private String imgUrl;
    private int price;

    public ProductEntity() {
    }

    public ProductEntity(String name, String imgUrl, int price) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.price = price;
    }

    public ProductEntity(int id, String name, String imgUrl, int price) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
