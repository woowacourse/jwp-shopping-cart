package cart.presentation.dto;

import cart.entity.ProductEntity;

import java.util.List;

public class CartRequest {

    private Integer memberId;
    private List<ProductEntity> products;

    public CartRequest() {
    }

    public CartRequest(Integer memberId, List<ProductEntity> products) {
        this.memberId = memberId;
        this.products = products;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public List<ProductEntity> getProducts() {
        return products;
    }
}
