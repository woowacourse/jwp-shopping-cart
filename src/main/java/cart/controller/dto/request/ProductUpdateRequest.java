package cart.controller.dto.request;

public class ProductUpdateRequest extends ProductRequest {

    public ProductUpdateRequest(String name, int price, String imageUrl) {
        super(name, price, imageUrl);
    }

}
