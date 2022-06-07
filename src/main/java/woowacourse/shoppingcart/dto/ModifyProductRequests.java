package woowacourse.shoppingcart.dto;

import java.util.List;

public class ModifyProductRequests {

    private List<ModifyProductRequest> products;

    private ModifyProductRequests() {
    }

    public ModifyProductRequests(List<ModifyProductRequest> products) {
        this.products = products;
    }

    public List<ModifyProductRequest> getProducts() {
        return products;
    }
}
