package woowacourse.shoppingcart.dto.customer.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.password.EncodedPassword;
import woowacourse.shoppingcart.domain.customer.password.PasswordEncoder;
import woowacourse.shoppingcart.domain.customer.password.RawPassword;

public class CustomerSaveRequest {

    @Pattern(regexp = "^[a-z0-9_-]{5,20}$",
            message = "유저 네임 형식이 올바르지 않습니다. (영문 소문자, 숫자와 특수기호(_), (-)만 사용 가능, 5자 이상 20자 이내)")
    private final String username;

    @Pattern(regexp = "^[a-z0-9._-]+@[a-z]+[.]+[a-z]{2,3}$",
            message = "이메일 형식이 올바르지 않습니다. (형식: example@email.com)")
    private final String email;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()])[A-Za-z\\d!@#$%^&*()]{8,16}$",
            message = "비밀번호 형식이 올바르지 않습니다. (영문자, 숫자, 특수문자!, @, #, $, %, ^, &, *, (, )를 모두 사용, 8자 이상 16자 이내)")
    private final String password;

    @NotEmpty(message = "주소는 필수 입력 사항압니다.")
    @Size(max = 255, message = "주소 형식이 올바르지 않습니다. (길이: 255 이하)")
    private final String address;

    @NotEmpty(message = "전화번호는 필수 입력사항입니다.")
    @Pattern(regexp = "\\d{3}-\\d{4}-\\d{4}", message = "전화번호 형식이 올바르지 않습니다. (형식: 000-0000-0000)")
    private final String phoneNumber;

    private CustomerSaveRequest() {
        this(null, null, null, null, null);
    }

    public CustomerSaveRequest(String username, String email, String password, String address,
            String phoneNumber) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public Customer toCustomer(PasswordEncoder passwordEncoder) {
        RawPassword rawPassword = new RawPassword(password);
        EncodedPassword encodedPassword = passwordEncoder.encode(rawPassword);
        return new Customer(username, email, encodedPassword, address, phoneNumber);
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
