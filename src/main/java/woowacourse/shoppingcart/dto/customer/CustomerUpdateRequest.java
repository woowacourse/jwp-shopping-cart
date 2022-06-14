package woowacourse.shoppingcart.dto.customer;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import woowacourse.shoppingcart.domain.Customer;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class CustomerUpdateRequest {

    @NotNull(message = "닉네임 1자 이상 10자 이하여야합니다.")
    private String username;

    public Customer toEntity() {
        return new Customer(null, username, null);
    }
}
