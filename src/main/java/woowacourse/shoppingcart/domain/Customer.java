package woowacourse.shoppingcart.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Customer {

    private static final Pattern PASSWORD_REGEX = Pattern.compile("^[a-zA-Z\\d!@#$%^*]+$");
    private static final int MIN_USERNAME_LENGTH = 4;
    private static final int MAX_USERNAME_LENGTH = 20;
    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int MAX_PASSWORD_LENGTH = 20;
    private static final int MIN_NICKNAME_LENGTH = 8;
    private static final int MAX_NICKNAME_LENGTH = 20;

    private final String username;
    private final String password;
    private final String nickname;
    private final int age;

    public Customer(String username, String password, String nickname, int age) {
        validate(username, password, nickname, age);
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.age = age;
    }

    private void validate(String username, String password, String nickname, int age) {
        validateUsername(username);
        validatePassword(password);
        validateNickname(nickname);
        validateAge(age);
    }

    private void validateUsername(String username) {
        validateLength(username, MIN_USERNAME_LENGTH, MAX_USERNAME_LENGTH);
    }

    private void validatePassword(String password) {
        validateLength(password, MIN_PASSWORD_LENGTH, MAX_PASSWORD_LENGTH);
        Matcher matcher = PASSWORD_REGEX.matcher(password);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("비밀번호는 영문 대소문자, 숫자, 특수문자(!@#$%^*) 조합이여야 합니다.");
        }
    }

    private void validateNickname(String nickname) {
        validateLength(nickname, MIN_NICKNAME_LENGTH, MAX_NICKNAME_LENGTH);
    }

    private void validateAge(int age) {
        if (age < 1) {
            throw new IllegalArgumentException("나이는 최소 1살이어야 합니다.");
        }
    }

    private void validateLength(String target, int min, int max) {
        if (min != 0 && target.isBlank()) {
            throw new IllegalArgumentException("공백은 허용되지 않습니다.");
        }
        if (target.length() < min || target.length() > max) {
            throw new IllegalArgumentException("길이는 " + min + "이상, " + max + "이하여야 합니다.");
        }
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    public int getAge() {
        return age;
    }

    public boolean hasSamePassword(String password) {
        return this.password.equals(password);
    }
}
