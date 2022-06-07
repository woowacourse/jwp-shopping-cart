package woowacourse.shoppingcart.dto.request;

import javax.validation.constraints.NotBlank;

public class DeleteCustomerDto {

    private final Character tempForJsonParse;
    @NotBlank(message = "비밀번호에는 공백이 들어가면 안됩니다.")
    private final String password;

    private DeleteCustomerDto(final Character tempForJsonParse, final String password) {
        this.tempForJsonParse = tempForJsonParse;
        this.password = password;
    }

    public DeleteCustomerDto(final String password) {
        this(null, password);
    }

    public String getPassword() {
        return password;
    }
}
