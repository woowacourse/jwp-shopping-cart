package cart.controller.dto.request;

public class ProductCreateRequest extends ProductRequest {

    public ProductCreateRequest() {
    }

    public ProductCreateRequest(
            final String name,
            final int price,
            final String imageUrl
    ) {
        super(name, price, imageUrl);
    }

}
