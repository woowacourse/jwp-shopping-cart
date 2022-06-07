package woowacourse.shoppingcart.dto.response;

public class ProductExistingInCartResponse {
    private final boolean exists;

    public ProductExistingInCartResponse(boolean exists) {
        this.exists = exists;
    }

    public boolean isExists() {
        return exists;
    }

    @Override
    public String toString() {
        return "CartProductExistsResponse{" +
                "exists=" + exists +
                '}';
    }
}
