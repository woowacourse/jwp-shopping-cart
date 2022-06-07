package woowacourse.shoppingcart.dto.request;

import javax.validation.constraints.NotBlank;

public class UpdateCustomerDto {

    private final Character tempForJsonParse;
    @NotBlank(message = "닉네임에는 공백이 들어가면 안됩니다.")
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
