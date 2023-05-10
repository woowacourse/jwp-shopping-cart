package cart.member.domain;

import lombok.Getter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
public class Email {

    static final int MAX_LENGTH = 100;

    private static final Pattern ADDRESS_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(\\S+)$");

    private final String address;

    public Email(String address) {
        final String stripped = address.strip();
        validateLength(stripped);
        validateAddressPattern(stripped);
        this.address = stripped;
    }

    private void validateLength(String address) {
        if (address.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("주소 길이는 100자까지 입니다");
        }
    }

    private void validateAddressPattern(String address) {
        final Matcher matcher = ADDRESS_PATTERN.matcher(address);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("올바르지 않은 주소 형식입니다");
        }
    }
}
