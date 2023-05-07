package cart.dto.mapper;

import cart.domain.cart.Item;
import cart.domain.product.Product;
import cart.dto.response.ItemResponse;
import cart.dto.response.ProductResponse;

import java.util.List;
import java.util.stream.Collectors;

public class ResponseMapper {

    public static List<ItemResponse> toItemResponses(List<Item> items) {
        return items.stream()
                .map(item -> new ItemResponse(
                        item.getId(),
                        item.getMemberId(),
                        toProductResponse(item.getProduct()))
                ).collect(Collectors.toList());
    }

    public static ProductResponse toProductResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getImageUrl()
        );
    }
}
