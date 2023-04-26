package cart.factory;

import cart.dto.ProductCreateRequestDto;
import cart.dto.ProductEditRequestDto;

public class ProductRequestDtoFactory {

    public static ProductEditRequestDto createProductEditRequest() {
        return new ProductEditRequestDto("치킨 수정", 10000, "urlEdit");
    }

    public static ProductCreateRequestDto createProductCreateRequest() {
        return new ProductCreateRequestDto("치킨", 10000, "url");
    }
}
