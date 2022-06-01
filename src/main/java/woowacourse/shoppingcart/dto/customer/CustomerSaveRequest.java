package woowacourse.shoppingcart.dto.customer;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import woowacourse.shoppingcart.domain.customer.Customer;

public class CustomerSaveRequest {

    @Pattern(regexp = "^[a-z0-9_-]{5,20}$",
            message = "유저 네임 형식이 올바르지 않습니다. (영문 소문자, 숫자와 특수기호(_), (-)만 사용 가능)")
    private String username;

    @Pattern(regexp = "^[a-z0-9._-]+@[a-z]+[.]+[a-z]{2,3}$",
            message = "이메일 형식이 올바르지 않습니다. (형식: example@email.com)")
    private String email;

    @NotEmpty(message = "비밀번호 필수 입력 사항압니다.")
    @Size(min = 8, max = 16, message = "비밀번호 길이가 올바르지 않습니다. (길이: 8 이상 16 이하)")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()])[A-Za-z\\d!@#$%^&*()]{8,16}$", message = "비밀번호 형식이 올바르지 않습니다. (영문자, 숫자, 특수문자!, @, #, $, %, ^, &, *, (, )를 모두 사용)")
    private String password;

    @NotEmpty(message = "주소는 필수 입력 사항압니다.")
    @Size(max = 255, message = "주소 형식이 올바르지 않습니다. (길이: 255 이하)")
    private String address;

    @NotEmpty(message = "전화번호는 필수 입력사항입니다.")
    @Pattern(regexp = "\\d{3}-\\d{4}-\\d{4}", message = "전화번호 형식이 올바르지 않습니다. (형식: 000-0000-0000)")
    private String phoneNumber;

    private CustomerSaveRequest() {
    }

    public CustomerSaveRequest(String username, String email, String password, String address,
            String phoneNumber) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public Customer toCustomer() {
        return new Customer(username, email, password, address, phoneNumber);
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
