package woowacourse.shoppingcart.ui.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import woowacourse.shoppingcart.service.dto.CustomerCreateServiceRequest;

public class CustomerRequest {
    @NotBlank
    private String name;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Length(min = 8, max = 15)
    private String password;

    public CustomerRequest() {
    }

    public CustomerRequest(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public CustomerCreateServiceRequest toServiceRequest() {
        return new CustomerCreateServiceRequest(this.name, this.email, this.password);
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
