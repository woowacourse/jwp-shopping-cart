package cart.controller.dto.request;

import cart.entity.ProductEntity;

public class ProductUpdateRequest extends ProductRequest {

    public ProductUpdateRequest() {
    }

    public ProductUpdateRequest(
            final String name,
            final int price,
            final String imageUrl
    ) {
        super(name, price, imageUrl);
    }

    public ProductEntity toEntity() {
        return ProductEntity.generate(this);
    }

}
