package woowacourse.shoppingcart.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import woowacourse.auth.dto.PhoneNumber;
import woowacourse.shoppingcart.domain.Customer;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Locale;

public class SignupRequest {

    private static final int ACCOUNT_MIN_SIZE = 4;
    private static final int ACCOUNT_MAX_SIZE = 15;
    private static final int NICKNAME_MIN_SIZE = 2;
    private static final int NICKNAME_MAX_SIZE = 10;
    private static final int PASSWORD_MIN_SIZE = 8;
    private static final int PASSWORD_MAX_SIZE = 20;
    private static final String PASSWORD_FORMAT_REGEX = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)).*";
    private static final int ADDRESS_MAX_SIZE = 255;
    @Size(min = ACCOUNT_MIN_SIZE, max = ACCOUNT_MAX_SIZE, message = "아이디 길이는 4~15자를 만족해야 합니다.")
    private final String account;
    @Size(min = NICKNAME_MIN_SIZE, max = NICKNAME_MAX_SIZE, message = "닉네임 길이는 2~10자를 만족해야 합니다.")
    private final String nickname;
    @Size(min = PASSWORD_MIN_SIZE, max = PASSWORD_MAX_SIZE, message = "비밀번호 길이는 8~20자를 만족해야 합니다.")
    @Pattern(regexp = PASSWORD_FORMAT_REGEX, message = "비밀번호는 대소문자, 숫자, 특수문자가 반드시 1개 이상 포함되어야 합니다.")
    private final String password;
    @Size(max = ADDRESS_MAX_SIZE, message = "주소 길이는 255자를 초과할 수 없습니다.")
    private final String address;
    @Valid
    private final PhoneNumber phoneNumber;

    @JsonCreator
    public SignupRequest(String account, String nickname, String password, String address, PhoneNumber phoneNumber) {
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

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }
}
