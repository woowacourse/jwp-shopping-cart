package woowacourse.shoppingcart.dto;

public class UpdateCustomerDto {

    private final Character tempForJsonParse;
    private final String username;

    private UpdateCustomerDto(final Character tempForJsonParse, final String username) {
        this.tempForJsonParse = tempForJsonParse;
        this.username = username;
    }

    public UpdateCustomerDto(final String username) {
        this(null, username);
    }

    public String getUsername() {
        return username;
    }
}
