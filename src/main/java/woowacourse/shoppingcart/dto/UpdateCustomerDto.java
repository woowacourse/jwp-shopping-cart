package woowacourse.shoppingcart.dto;

import javax.validation.constraints.NotBlank;

public class UpdateCustomerDto {

    @NotBlank(message = "닉네임에는 공백이 들어가면 안됩니다.")
    private String username;

    private UpdateCustomerDto() {
    }

    public UpdateCustomerDto(final String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
