package woowacourse.shoppingcart.dto.response;

import java.util.List;

public class GetCartItemsResponse {

    private List<GetCartItemResponse> products;

    public GetCartItemsResponse() {
    }

    public GetCartItemsResponse(List<GetCartItemResponse> products) {
        this.products = products;
    }

    public List<GetCartItemResponse> getProducts() {
        return products;
    }

    public void setProducts(List<GetCartItemResponse> products) {
        this.products = products;
    }
}
