package cart.factory;

import cart.dto.api.request.ProductCreateRequest;
import cart.dto.api.request.ProductEditRequest;

public class ProductRequestDtoFactory {

    public static ProductEditRequest createProductEditRequest() {
        return new ProductEditRequest("치킨 수정", 10000, "urlEdit");
    }

    public static ProductCreateRequest createProductCreateRequest() {
        return new ProductCreateRequest("치킨", 10000, "url");
    }
}
