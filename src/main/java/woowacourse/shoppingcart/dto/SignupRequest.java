package woowacourse.shoppingcart.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class SignupRequest {

    @NotNull
    @Size(min = 3, max = 15, message = "username의 길이는 3자 이상 15자 이하여야 합니다.")
    @Pattern(regexp = "^[0-9a-zA-Z]*$", message = "username는 영어 또는 숫자로 이루어져야 합니다.")
    private String username;

    @NotNull
    @Size(min = 8, max = 20, message = "password의 길이는 8자 이상 20자 이하여야 합니다.")
    @Pattern(regexp = "^[0-9a-zA-Z]*$", message = "password는 영어 또는 숫자로 이루어져야 합니다.")
    private String password;

    @NotNull
    @Pattern(regexp = "^01(?:0|1|[6-9])(\\d{4})(\\d{4})$", message = "phoneNumber는 휴대폰번호 형식으로 입력해야 합니다.")
    private String phoneNumber;

    @NotNull
    private String address;

    public SignupRequest() {
    }

    public SignupRequest(String username, String password, String phoneNumber, String address) {
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
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
