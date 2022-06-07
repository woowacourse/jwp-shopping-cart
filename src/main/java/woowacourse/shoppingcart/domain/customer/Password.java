package woowacourse.shoppingcart.domain.customer;

import woowacourse.shoppingcart.support.Encryptor;

import java.util.OptionalInt;

public class Password {

    private static final int PASSWORD_MIN_LENGTH = 8;
    private static final int PASSWORD_MAX_LENGTH = 16;

    private final String password;

    private Password(String password) {
        this.password = password;
    }

    public static Password toPasswordWithEncrypt(String rawPassword, Encryptor encryptor) {
        Password password = new Password(rawPassword);
        password.validatePassword();
        return new Password(encryptor.encrypt(password.value()));
    }

    public static Password toPassword(String password) {
        return new Password(password);
    }

    private void validatePassword() {
        if (isLengthRange() && isContainsLetter()) {
            return;
        }
        throw new IllegalArgumentException("패스워드는 영문 대문자, 소문자, 숫자, 특수문자 한자리씩 포함한 " +
                "8~16자리로 구성하여야 합니다.");
    }

    private boolean isLengthRange() {
        return password.length() >= PASSWORD_MIN_LENGTH && password.length() <= PASSWORD_MAX_LENGTH;
    }

    private boolean isContainsLetter() {
        return isContainsLowerCase(password) &&
                isContainsUpperCase(password) &&
                isContainsSpecialCase(password) &&
                isContainsNumber(password);
    }


    private boolean isContainsLowerCase(String text) {
        OptionalInt result = text.chars()
                .filter(ch -> Character.isAlphabetic(ch) && Character.isLowerCase(ch))
                .findAny();

        return result.isPresent();
    }

    private boolean isContainsUpperCase(String text) {
        OptionalInt result = text.chars()
                .filter(ch -> Character.isAlphabetic(ch) && Character.isUpperCase(ch))
                .findAny();

        return result.isPresent();
    }

    private boolean isContainsSpecialCase(String text) {
        final String special = "!@#$%^&*-_";
        OptionalInt result = text.chars()
                .filter(ch -> special.indexOf(ch) != -1)
                .findAny();

        return result.isPresent();
    }

    private boolean isContainsNumber(String text) {
        OptionalInt result = text.chars()
                .filter(Character::isDigit)
                .findAny();

        return result.isPresent();
    }

    public String value() {
        return password;
    }
}
