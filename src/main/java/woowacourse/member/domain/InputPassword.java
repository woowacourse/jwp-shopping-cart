package woowacourse.member.domain;

import woowacourse.member.exception.FailedEncryptException;
import woowacourse.member.exception.InvalidPasswordException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

public class InputPassword extends Password {

    private static final Pattern casePattern = Pattern.compile("(?=.*?[a-z])(?=.*?[A-Z])");
    private static final Pattern specialCharacterPattern = Pattern.compile("(?=.*?[!@?-])");

    public InputPassword(String value) {
        super(encrypt(value));
        validate(value);
    }

    private void validate(String value) {
        validateLength(value);
        validateCase(value);
        validateContainsSpecialCharacters(value);
    }

    private void validateLength(String value) {
        if (value.length() < 6) {
            throw new InvalidPasswordException("비밀번호는 6글자 이상이어야 합니다.");
        }
    }

    private void validateCase(String value) {
        if (!casePattern.matcher(value).find()) {
            throw new InvalidPasswordException("비밀번호는 대소문자를 포함해야 합니다.");
        }
    }

    private void validateContainsSpecialCharacters(String value) {
        if (!specialCharacterPattern.matcher(value).find()) {
            throw new InvalidPasswordException("비밀번호는 특수문자(!,@,?,-)를 포함해야 합니다");
        }
    }

    private static String encrypt(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(text.getBytes());
            return bytesToHex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new FailedEncryptException();
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }
}
