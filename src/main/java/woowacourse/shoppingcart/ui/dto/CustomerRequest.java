package woowacourse.shoppingcart.ui.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class CustomerRequest {

    @NotEmpty(message = "이름은 비어있을 수 없습니다.")
    @Size(min = 5, max = 20)
    private final String username;

    @NotEmpty(message = "패스워드는 비어있을 수 없습니다.")
    @Size(min = 8, max = 16)
    private final String password;

    @NotEmpty(message = "이메일은 비어있을 수 없습니다.")
    @Email
    private final String email;

    @NotEmpty(message = "주소는 비어있을 수 없습니다.")
    @Size(max = 255)
    private final String address;

    @NotEmpty(message = "전화번호는 비어있을 수 없습니다.")
    private final String phoneNumber;

    private CustomerRequest() {
        this(null, null, null, null, null);
    }

    public CustomerRequest(String username, String password, String email, String address, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
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
