package woowacourse.shoppingcart.dto;

public class AddProductRequest {

    private Long product_id;

    private AddProductRequest() {
    }

    public AddProductRequest(Long product_id) {
        this.product_id = product_id;
    }

    public Long getProduct_id() {
        return product_id;
    }
}
