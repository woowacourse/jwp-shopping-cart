package woowacourse.shoppingcart.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class CustomerRequest {
    @Email(message = "Invalid value")
    @NotEmpty(message = "Invalid value")
    private String email;
    private String password;
    @NotEmpty(message = "Invalid value")
    @Size(min = 1, max = 30, message = "Invalid value")
    private String username;

    public CustomerRequest() {
    }

    public CustomerRequest(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
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
