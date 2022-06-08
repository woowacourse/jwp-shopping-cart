package woowacourse.shoppingcart.dto.customer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import woowacourse.shoppingcart.domain.Customer;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class CustomerResponse {

    private Long id;
    private String email;
    private String username;

    public CustomerResponse(Customer customer) {
        this(customer.getId(), customer.getEmail(), customer.getUsername());
    }
}
