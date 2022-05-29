package woowacourse.shoppingcart.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import woowacourse.auth.dto.PhoneNumber;
import woowacourse.shoppingcart.domain.Customer;

import javax.validation.constraints.Size;
import java.util.Locale;

public class SignupRequest {

    @Size(min = 4, max = 15, message = "아이디 길이는 4~15자를 만족해야 합니다.")
    private final String account;
    @Size(min = 2, max = 20, message = "닉네임 길이는 2~20자를 만족해야 합니다.")
    private final String nickname;
    private final String password;
    private final String address;
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
