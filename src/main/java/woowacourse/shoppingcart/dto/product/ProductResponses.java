package woowacourse.shoppingcart.dto.product;

import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import woowacourse.shoppingcart.domain.Product;

@NoArgsConstructor(access = AccessLevel.NONE)
@Getter
public class ProductResponses {

    public static List<ProductResponse> from(List<Product> products) {
        List<ProductResponse> responses = new ArrayList<>();
        for (Product product : products) {
            ProductResponse response = new ProductResponse(product);
            responses.add(response);
        }
        return responses;
    }
}
