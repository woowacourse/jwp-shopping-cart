package woowacourse.shoppingcart.dto;

public class ProductExistenceResponse {
    private boolean exists;

    public ProductExistenceResponse() {
    }

    public ProductExistenceResponse(boolean exists) {
        this.exists = exists;
    }

    public boolean getExists() {
        return exists;
    }

    @Override
    public String toString() {
        return "ProductExistenceResponse{" +
                "exists=" + exists +
                '}';
    }
}
