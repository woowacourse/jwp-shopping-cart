package woowacourse.shoppingcart.dto.response;

public class ShoppingCartErrorResponse {

    private final String message;

    private ShoppingCartErrorResponse(String message) {
        this.message = message;
    }

    public static ShoppingCartErrorResponse from(String message) {
        return new ShoppingCartErrorResponse(message);
    }

    public String getMessage() {
        return message;
    }
}
