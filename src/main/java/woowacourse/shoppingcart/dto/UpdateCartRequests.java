package woowacourse.shoppingcart.dto;

import java.util.List;
import java.util.stream.Collectors;
import woowacourse.shoppingcart.domain.Cart;

public class UpdateCartRequests {

    private List<UpdateCartRequest> products;

    public UpdateCartRequests() {
    }

    public UpdateCartRequests(List<UpdateCartRequest> products) {
        this.products = products;
    }

    public List<UpdateCartRequest> getProducts() {
        return products;
    }

    public List<Cart> carts() {
        return products.stream()
                .map(UpdateCartRequest::toCart)
                .collect(Collectors.toList());
    }
}
