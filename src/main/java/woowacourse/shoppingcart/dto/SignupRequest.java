package woowacourse.shoppingcart.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class SignupRequest {

    @NotBlank
    private final String account;
    @NotBlank
    private final String nickname;
    @NotBlank
    private final String password;
    @NotBlank
    private final String address;
    @NotNull
    @Valid
    private final PhoneNumberFormat phoneNumber;

    @JsonCreator
    public SignupRequest(String account, String nickname, String password, String address, PhoneNumberFormat phoneNumber) {
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
