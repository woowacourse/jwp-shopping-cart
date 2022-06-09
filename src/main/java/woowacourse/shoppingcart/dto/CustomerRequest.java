package woowacourse.shoppingcart.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CustomerRequest {

    @NotBlank
    @Size(min = 5, max = 20)
    private final String name;

    @NotBlank
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*()]).+$")
    @Size(min = 8, max = 16)
    private final String password;

    @NotBlank
    @Pattern(regexp = "^[a-z0-9-_.]+@[a-z]+[.][a-z]{2,3}")
    @Size(max = 255)
    private final String email;

    @NotBlank
    @Size(max = 255)
    private final String address;

    @NotBlank
    @Pattern(regexp = "^010-[0-9]{4}-[0-9]{4}")
    private final String phoneNumber;

    private CustomerRequest() {
        this(null, null, null, null, null);
    }

    public CustomerRequest(String name, String password, String email, String address, String phoneNumber) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
