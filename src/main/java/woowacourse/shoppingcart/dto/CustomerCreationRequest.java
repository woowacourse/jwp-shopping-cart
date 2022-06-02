package woowacourse.shoppingcart.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import woowacourse.shoppingcart.domain.customer.Customer;

public class CustomerCreationRequest {

    @Email(message = "이메일 양식이 잘못 되었습니다.")
    private String email;

    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-zA-Z])[0-9a-zA-Z]{8,20}", message = "비밀번호 양식이 잘못 되었습니다.")
    private String password;

    @Pattern(regexp = "[a-zA-Z0-9가-힣]{2,}", message = "닉네임 양식이 잘못 되었습니다.")
    private String nickname;

    private CustomerCreationRequest() {
    }

    public CustomerCreationRequest(final String email, final String password, final String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    public Customer toCustomer() {
        return new Customer(
                nickname,
                email,
                password
        );
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }
}
