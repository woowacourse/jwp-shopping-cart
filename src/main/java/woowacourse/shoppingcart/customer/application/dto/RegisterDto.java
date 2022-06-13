package woowacourse.shoppingcart.customer.application.dto;

import woowacourse.shoppingcart.customer.ui.dto.CustomerRequest;

public class RegisterDto {
    private final String email;
    private final String password;
    private final String username;

    private RegisterDto(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public static RegisterDto from(CustomerRequest customerRequest) {
        return new RegisterDto(customerRequest.getEmail(), customerRequest.getPassword(),
                customerRequest.getUsername());
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
