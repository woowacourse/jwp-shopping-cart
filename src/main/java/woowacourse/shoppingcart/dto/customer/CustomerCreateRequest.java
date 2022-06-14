package woowacourse.shoppingcart.dto.customer;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import woowacourse.shoppingcart.domain.Customer;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Setter
@Getter
public class CustomerCreateRequest {

//    @Email(message = "이메일 형식을 지켜야합니다.")
//    @Size(min = 8, max = 50, message = "이메일은 8자 이상 50자 이하여야합니다.")
//    @Pattern(regexp = "\\S*", message = "이메일에는 공백이 들어가면 안됩니다.")
    @NotNull(message = "이메일은 8자 이상 50자 이하여야합니다.")
    private String email;

//    @Size(min = 1, max = 10, message = "닉네임 1자 이상 10자 이하여야합니다.")
//    @Pattern(regexp = "\\S*", message = "닉네임에는 공백이 들어가면 안됩니다.")
    @NotNull(message = "닉네임 1자 이상 10자 이하여야합니다.")
    private String username;

//    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하여야합니다.")
//    @Pattern(regexp = "\\S*", message = "비밀번호에는 공백이 들어가면 안됩니다.")
    @NotNull(message = "비밀번호는 8자 이상 20자 이하여야합니다.")
    private String password;

    public Customer toEntity() {
        return new Customer(email, username, password);
    }
}
