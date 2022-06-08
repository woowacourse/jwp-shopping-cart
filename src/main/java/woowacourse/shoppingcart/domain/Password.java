package woowacourse.shoppingcart.domain;

import woowacourse.shoppingcart.support.Encryptor;
import woowacourse.shoppingcart.support.SHA256Encryptor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Password {
    private final String password;
    private final Encryptor encryptor = new SHA256Encryptor();

    public Password(String password) {
        this.password = password;
    }

    public void validateFormat() {
        checkLength();
        checkPattern();
    }

    private void checkLength() {
        if (password.length() < 8 || password.length() > 16) {
            throw new IllegalArgumentException("비밀번호는 8자 이상 16자 이하이어야 합니다.");
        }
    }

    private void checkPattern() {
        String regExpPw = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[^a-zA-Z0-9ㄱ-힣])";
        Matcher matcher = Pattern.compile(regExpPw).matcher(password);
        if(!matcher.find()){
            throw new IllegalArgumentException("비밀번호는 소문자, 대문자, 숫자, 특수문자를 모두 포함해야 합니다.");
        }
    }

    public String encryptPassword() {
        return encryptor.encrypt(password);
    }
}
