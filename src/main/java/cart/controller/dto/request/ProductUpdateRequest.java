package cart.controller.dto.request;

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

}
