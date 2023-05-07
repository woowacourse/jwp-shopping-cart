package cart.presentation.dto;

import cart.entity.Product;

import java.util.List;

public class CartRequest {

    private Integer memberId;
    private List<Product> products;

    public CartRequest() {
    }

    public CartRequest(Integer memberId, List<Product> products) {
        this.memberId = memberId;
        this.products = products;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public List<Product> getProducts() {
        return products;
    }
}
