package woowacourse.shoppingcart.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import woowacourse.auth.dto.PhoneNumber;
import woowacourse.shoppingcart.domain.Customer;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Locale;

public class SignupRequest {

    @Size(min = 4, max = 15, message = "아이디 길이는 4~15자를 만족해야 합니다.")
    private final String account;
    @Size(min = 2, max = 10, message = "닉네임 길이는 2~10자를 만족해야 합니다.")
    private final String nickname;
    @Size(min = 8, max = 20, message = "비밀번호 길이는 8~20자를 만족해야 합니다.")
    @Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)).*", message = "비밀번호는 대소문자, 숫자, 특수문자가 반드시 1개 이상 포함되어야 합니다.")
    private final String password;
    @Size(max = 255, message = "주소 길이는 255자를 초과할 수 없습니다.")
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

    public Customer toEntity() {
        String match = "[^\\da-zA-Z]";
        final String processedAccount = this.account.replaceAll(match, "").toLowerCase(Locale.ROOT).trim();

        return new Customer(processedAccount, this.nickname, this.password, this.address, this.phoneNumber.appendNumbers());
    }
}
