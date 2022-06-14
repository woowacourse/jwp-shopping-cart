package woowacourse.shoppingcart.dto.customer;

import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import woowacourse.shoppingcart.domain.Customer;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Setter
@Getter
public class CustomerCreateRequest {

    @NotNull(message = "이메일은 8자 이상 50자 이하여야합니다.")
    private String email;

    @NotNull(message = "닉네임 1자 이상 10자 이하여야합니다.")
    private String username;

    @NotNull(message = "비밀번호는 8자 이상 20자 이하여야합니다.")
    private String password;

    public static CustomerCreateRequest from(String email, String username, String password) {
        return new CustomerCreateRequest(email, username, password);
    }

    public Customer toEntity() {
        return new Customer(email, username, password);
    }
}
