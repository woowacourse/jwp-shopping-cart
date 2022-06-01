package woowacourse.auth.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.Valid;
import javax.validation.constraints.Size;

public class UpdateCustomerRequest {

    private static final int NICKNAME_MIN_SIZE = 2;
    private static final int NICKNAME_MAX_SIZE = 10;
    private static final int ADDRESS_MAX_SIZE = 255;

    @Size(min = NICKNAME_MIN_SIZE, max = NICKNAME_MAX_SIZE, message = "닉네임 길이는 2~10자를 만족해야 합니다.")
    private final String nickname;
    @Size(max = ADDRESS_MAX_SIZE, message = "주소 길이는 255자를 초과할 수 없습니다.")
    private final String address;
    @Valid
    private final PhoneNumber phoneNumber;

    @JsonCreator
    public UpdateCustomerRequest(String nickname, String address, PhoneNumber phoneNumber) {
        this.nickname = nickname;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getNickname() {
        return nickname;
    }

    public String getAddress() {
        return address;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }
}
