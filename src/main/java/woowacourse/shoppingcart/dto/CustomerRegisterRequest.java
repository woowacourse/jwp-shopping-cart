package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.application.dto.CustomerSaveServiceRequest;

public class CustomerRegisterRequest {

    private String name;
    private String email;
    private String password;

    private CustomerRegisterRequest() {
    }

    public CustomerRegisterRequest(final String name, final String email, final String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public CustomerSaveServiceRequest toServiceDto() {
        return new CustomerSaveServiceRequest(name, email, password);
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
