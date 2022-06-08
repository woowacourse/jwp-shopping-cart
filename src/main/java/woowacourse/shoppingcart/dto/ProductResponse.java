package woowacourse.shoppingcart.dto;

public class ProductResponse {
    private Long id;
    private String name;
    private Integer price;
    private String imageUrl;
    private int quantity;

    private ProductResponse(){}

    public ProductResponse(Long id, int quantity) {
        this.id= id;
        this.quantity = quantity;
    }
}
