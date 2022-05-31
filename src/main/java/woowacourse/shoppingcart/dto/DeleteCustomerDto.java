package woowacourse.shoppingcart.dto;

public class DeleteCustomerDto {

    private final Character tempForJsonParse;
    private final String password;

    private DeleteCustomerDto(final Character tempForJsonParse, final String password) {
        this.tempForJsonParse = tempForJsonParse;
        this.password = password;
    }

    public DeleteCustomerDto(final String password){
        this(null, password);
    }

    public String getPassword() {
        return password;
    }
}
