package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Id;

import java.util.List;

public class DeleteProductRequest {

    private List<Id> products;

    private DeleteProductRequest() {
    }

    public DeleteProductRequest(List<Id> products) {
        this.products = products;
    }

    public List<Id> getProducts() {
        return products;
    }
}
