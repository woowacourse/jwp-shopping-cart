package woowacourse.shoppingcart.ui.dto;

import javax.validation.constraints.NotEmpty;

public class CustomerRequest {

    @NotEmpty(message = "이름은 비어있을 수 없습니다.")
    private final String name;
    @NotEmpty(message = "패스워드는 비어있을 수 없습니다.")
    private final String password;
    @NotEmpty(message = "이메일은 비어있을 수 없습니다.")
    private final String email;
    @NotEmpty(message = "주소는 비어있을 수 없습니다.")
    private final String address;
    @NotEmpty(message = "전화번호는 비어있을 수 없습니다.")
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
