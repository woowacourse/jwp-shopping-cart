package cart.controller.dto.request;

public class ProductCreateRequest extends ProductRequest {

    public ProductCreateRequest(String name, int price, String imageUrl) {
        super(name, price, imageUrl);
    }

}
