package woowacourse.shoppingcart.dto.customer;

public class EmailUniqueCheckResponse {

    private final boolean unique;

    public EmailUniqueCheckResponse(boolean unique) {
        this.unique = unique;
    }

    public boolean getUnique() {
        return unique;
    }
}
