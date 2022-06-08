package woowacourse.shoppingcart.dto.product;

import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import woowacourse.shoppingcart.domain.Product;

@Getter
public class ProductResponses {

    private ProductResponses() {
    }

    public static List<ProductResponse> from(List<Product> products) {
        List<ProductResponse> response = new ArrayList<>();
        for (Product product : products) {
            ProductResponse productResponse = new ProductResponse(product);
            response.add(productResponse);
        }
        return response;
    }
}
