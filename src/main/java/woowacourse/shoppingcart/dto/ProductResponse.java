package woowacourse.shoppingcart.dto;

public class ProductResponse {

    private Long id;
    private String name;
    private int price;
    private String image_url;

    public ProductResponse() {
    }

    public ProductResponse(Long id, String name, int price, String image_url) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image_url = image_url;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImage_url() {
        return image_url;
    }
}
