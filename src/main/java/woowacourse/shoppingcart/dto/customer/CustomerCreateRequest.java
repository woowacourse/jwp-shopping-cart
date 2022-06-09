package woowacourse.shoppingcart.dto.customer;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.EncodedPassword;

public class CustomerCreateRequest {

    @Email(message = "이메일 형식을 지켜야합니다.")
    @Size(min = 8, max = 50, message = "이메일은 8자 이상 50자 이하여야합니다.")
    @Pattern(regexp = "\\S*", message = "이메일에는 공백이 들어가면 안됩니다.")
    private String email;

    @Size(min = 1, max = 10, message = "닉네임 1자 이상 10자 이하여야합니다.")
    @Pattern(regexp = "\\S*", message = "닉네임에는 공백이 들어가면 안됩니다.")
    private String username;

    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하여야합니다.")
    @Pattern(regexp = "\\S*", message = "비밀번호에는 공백이 들어가면 안됩니다.")
    private String password;

    private CustomerCreateRequest() {
    }

    public CustomerCreateRequest(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public Customer toEntity() {
        return new Customer(email, username, new EncodedPassword(password));
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
