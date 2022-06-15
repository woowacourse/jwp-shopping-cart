package woowacourse.shoppingcart.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class SignupRequest {

    private static final String PASSWORD_FORMAT_REGEX = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)).*";
    @Size(min = 4, max = 15, message = "아이디 길이는 4~15자를 만족해야 합니다.")
    @Pattern(regexp = "[a-zA-Z\\d]\\w+", message = "한글 아이디는 허용되지 않습니다.")
    private final String account;
    @Size(min = 2, max = 10, message = "닉네임 길이는 2~10자를 만족해야 합니다.")
    private final String nickname;
    @Size(min = 8, max = 20, message = "비밀번호 길이는 8~20자를 만족해야 합니다.")
    @Pattern(regexp = PASSWORD_FORMAT_REGEX, message = "비밀번호는 대소문자, 숫자, 특수문자가 반드시 1개 이상 포함되어야 합니다.")
    private final String password;
    @Size(max = 255, message = "주소 길이는 255자를 초과할 수 없습니다.")
    private final String address;
    @Valid
    private final PhoneNumberFormat phoneNumber;

    @JsonCreator
    public SignupRequest(String account, String nickname, String password, String address,
                         PhoneNumberFormat phoneNumber) {
        this.account = account;
        this.nickname = nickname;
        this.password = password;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getAccount() {
        return account;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public PhoneNumberFormat getPhoneNumber() {
        return phoneNumber;
    }
}
