package woowacourse.shoppingcart.dto.customer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class CustomerUpdateProfileRequest {

    @NotBlank(message = "닉네임이 입력되지 않았습니다.")
    @Pattern(regexp = "^(?=.*[a-z0-9가-힣ㄱ-ㅎㅏ-ㅣ])[a-z0-9가-힣ㄱ-ㅎㅏ-ㅣ]{2,10}$",
            message = "닉네임은 영문, 한글, 숫자를 조합하여 2 ~ 10 자를 입력해주세요.")
    private String nickname;

    @NotBlank(message = "비밀번호가 입력되지 않았습니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,16}$",
            message = "비밀번호는 영문, 특수문자, 숫자를 필수로 조합하여 8 ~ 16 자를 입력해주세요.")
    private String password;

    private CustomerUpdateProfileRequest() {
    }

    public CustomerUpdateProfileRequest(final String nickname, final String password) {
        this.nickname = nickname;
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }
}
