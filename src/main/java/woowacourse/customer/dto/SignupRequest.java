package woowacourse.customer.dto;

import javax.validation.constraints.NotBlank;

import woowacourse.customer.domain.Customer;

public class SignupRequest {

    @NotBlank(message = "사용자 이름을 입력해 주세요.")
    private String username;

    @NotBlank(message = "비밀번호를 입력해 주세요.")
    private String password;

    @NotBlank(message = "전화번호를 입력해 주세요.")
    private String phoneNumber;

    @NotBlank(message = "주소를 입력해 주세요.")
    private String address;

    private SignupRequest() {
    }

    public SignupRequest(final String username, final String password, final String phoneNumber, final String address) {
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public Customer toCustomer(final String password) {
        return Customer.of(username, password, phoneNumber, address);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }
}
