package cart.dao.entity;

public class CartProduct {
    private Long id;
    private Long productId;
    private String name;
    private Integer price;
    private String imgUrl;

    public CartProduct() {
    }

    public CartProduct(Long id, Long productId, String name, Integer price, String imgUrl) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
