package cart.presentation.dto;

import cart.entity.Product;

import java.util.List;

public class CartResponse {

    private Integer id;
    private Integer memberId;
    private List<Product> products;

    public CartResponse() {
    }

    public CartResponse(Integer id, Integer memberId, List<Product> products) {
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

    public List<Product> getProducts() {
        return products;
    }
}
