package woowacourse.shoppingcart.dto.response;

public class ExistedProductResponse {

    private boolean exists;

    private ExistedProductResponse() {}

    public ExistedProductResponse(boolean exists) {
        this.exists = exists;
    }

    public boolean isExists() {
        return exists;
    }
}
