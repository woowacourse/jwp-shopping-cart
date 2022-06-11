package woowacourse.shoppingcart.dto;

import javax.validation.constraints.NotBlank;

public class DeleteCustomerDto {

    @NotBlank(message = "비밀번호에는 공백이 들어가면 안됩니다.")
    private String password;

    private DeleteCustomerDto() {
    }

    public DeleteCustomerDto(final String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
