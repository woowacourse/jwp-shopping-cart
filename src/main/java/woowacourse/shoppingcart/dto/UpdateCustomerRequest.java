package woowacourse.shoppingcart.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.Valid;
import javax.validation.constraints.Size;

public class UpdateCustomerRequest {

    @Size(min = 2, max = 10, message = "닉네임 길이는 2~10자를 만족해야 합니다.")
    private final String nickname;
    @Size(max = 255, message = "주소 길이는 255자를 초과할 수 없습니다.")
    private final String address;
    @Valid
    private final PhoneNumberFormat phoneNumber;

    @JsonCreator
    public UpdateCustomerRequest(final String nickname, final String address, final PhoneNumberFormat phoneNumber) {
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

    public PhoneNumberFormat getPhoneNumber() {
        return phoneNumber;
    }
}
