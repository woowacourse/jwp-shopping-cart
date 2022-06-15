package woowacourse.shoppingcart.dto.customer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CustomerDeleteRequest {

    private String password;

    public static CustomerDeleteRequest from(String password) {
        return new CustomerDeleteRequest(password);
    }
}
