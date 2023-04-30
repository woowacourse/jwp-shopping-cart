package cart.member.domain;

import lombok.Getter;

import java.math.BigInteger;

@Getter
public class PhoneNumber {

    static final int MIN_LENGTH = 10;
    static final int MAX_LENGTH = 20;

    private final String number;

    public PhoneNumber(String number) {
        final String stripped = number.strip();
        validateLength(stripped);
        validateNumberFormat(stripped);
        this.number = stripped;
    }

    private void validateLength(String number) {
        if (number.length() < MIN_LENGTH || number.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("전화번호 길이는 10자 이상 20자 이하입니다");
        }
    }

    private void validateNumberFormat(String number) {
        try {
            new BigInteger(number);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자만 입력해주세요");
        }
    }
}
