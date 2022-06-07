package woowacourse.shoppingcart.dto.customer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class CustomerUpdateProfileRequest {

    @NotBlank(message = "닉네임이 입력되지 않았습니다.")
    @Pattern(regexp = "^(?=.*[a-z0-9가-힣ㄱ-ㅎㅏ-ㅣ])[a-z0-9가-힣ㄱ-ㅎㅏ-ㅣ]{2,10}$",
            message = "닉네임은 영문, 한글, 숫자를 조합하여 2 ~ 10 자를 입력해주세요.")
    private String nickname;

    private CustomerUpdateProfileRequest() {
    }

    public CustomerUpdateProfileRequest(final String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}
