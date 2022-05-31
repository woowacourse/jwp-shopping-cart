package woowacourse.shoppingcart.domain;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Customer {

    private static final Pattern LOWER_CASE_PATTERN = Pattern.compile(".*[a-z].*");
    private static final Pattern UPPER_CASE_PATTERN = Pattern.compile(".*[A-Z].*");
    private static final Pattern SPECIAL_CHARACTERS_PATTERN = Pattern.compile(".*[!@#$%^&*].*");
    private static final Pattern NUMBERS_PATTERN = Pattern.compile(".*[0-9].*");
    private static final Pattern SIZE_PATTERN = Pattern.compile("^(?=.{8,20}$).*");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^(.+)@(.+)$");
    private static final Pattern NICKNAME_PATTERN = Pattern.compile("^[A-Za-z0-9ㄱ-ㅎㅏ-ㅣ가-힣]{1,10}$");

    private final Long id;
    private final String email;
    private final String nickname;
    private String password;

    public Customer(Long id, String email, String nickname, String password) {
        validateEmailFormat(email);
        validatePasswordFormat(password);
        validateNicknameFormat(nickname);
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

    public Customer(String email, String nickname, String password) {
        this(null, email, nickname, password);
    }

    public boolean isPasswordMatched(String password) {
        return this.password.equals(password);
    }

    private void validateEmailFormat(String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("이메일 형식이 맞지 않습니다.");
        }
    }

    private void validatePasswordFormat(String password) {
        Matcher lowerCaseMatcher = LOWER_CASE_PATTERN.matcher(password);
        Matcher upperCaseMatcher = UPPER_CASE_PATTERN.matcher(password);
        Matcher specialCharactersMatcher = SPECIAL_CHARACTERS_PATTERN.matcher(password);
        Matcher numbersMatcher = NUMBERS_PATTERN.matcher(password);
        Matcher sizeMatcher = SIZE_PATTERN.matcher(password);

        if (!(lowerCaseMatcher.matches() && upperCaseMatcher.matches()
            && specialCharactersMatcher.matches() &&
            numbersMatcher.matches() && sizeMatcher.matches())) {
            throw new IllegalArgumentException("패스워드 형식이 맞지 않습니다.");
        }
    }

    private void validateNicknameFormat(String nickname) {
        Matcher nicknameMatcher = NICKNAME_PATTERN.matcher(nickname);

        if (!nicknameMatcher.matches()) {
            throw new IllegalArgumentException("닉네임 형식이 맞지 않습니다.");
        }
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void changePassword(String prevPassword, String newPassword) {
        if (!password.equals(prevPassword)) {
            throw new IllegalArgumentException("이전 비밀번호가 틀렸습니다.");
        }
        password = newPassword;
    }
}
