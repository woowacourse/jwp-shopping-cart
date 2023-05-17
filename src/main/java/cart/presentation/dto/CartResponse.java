package cart.presentation.dto;

import cart.entity.ProductEntity;

import java.util.List;

public class CartResponse {

    private Integer id;
    private Integer memberId;
    private List<ProductEntity> products;

    public CartResponse() {
    }

    public CartResponse(Integer id, Integer memberId, List<ProductEntity> products) {
        this.id = id;
        this.memberId = memberId;
        this.products = products;
    }

    public Integer getId() {
        return id;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public List<ProductEntity> getProducts() {
        return products;
    }
}
