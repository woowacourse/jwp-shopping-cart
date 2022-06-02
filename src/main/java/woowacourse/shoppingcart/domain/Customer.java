package woowacourse.shoppingcart.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Customer {

    private Long id;
    private final String email;
    private final String username;
    private final String password;

    public Customer(String email, String username, String password) {
        this(null, email, username, password);
    }
}
