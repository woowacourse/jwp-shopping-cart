package cart.domain;

import cart.exception.GlobalException;

import static cart.exception.ErrorCode.USER_EMAIL_LENGTH;
import static cart.exception.ErrorCode.USER_NICKNAME_LENGTH;
import static cart.exception.ErrorCode.USER_PASSWORD_LENGTH;

public class Member {

    private static final int EMAIl_MIN_LENGTH = 5, EMAIL_MAX_LENGTH = 50;
    private static final int PASSWORD_MIN_LENGTH = 8, PASSWORD_MAX_LENGTH = 50;
    private static final int NICKNAME_MIN_LENGTH = 1, NICKNAME_MAX_LENGTH = 30;

    private final String email;
    private final String password;
    private final String nickname;
    private final String telephone;

    private Member(final String email, final String password, final String nickname, final String telephone) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.telephone = telephone;
    }

    public static Member create(final String email, final String password, final String nickname, final String telephone) {
        validateEmail(email);
        validatePassword(password);
        validateNickname(nickname);
        return new Member(email, password, nickname, telephone);
    }

    private static void validateEmail(final String email) {
        if (email.length() < EMAIl_MIN_LENGTH || email.length() > EMAIL_MAX_LENGTH) {
            throw new GlobalException(USER_EMAIL_LENGTH);
        }
    }

    private static void validatePassword(final String password) {
        if (password.length() < PASSWORD_MIN_LENGTH || password.length() > PASSWORD_MAX_LENGTH) {
            throw new GlobalException(USER_PASSWORD_LENGTH);
        }
    }

    private static void validateNickname(final String nickname) {
        if (nickname.length() < NICKNAME_MIN_LENGTH || nickname.length() > NICKNAME_MAX_LENGTH) {
            throw new GlobalException(USER_NICKNAME_LENGTH);
        }
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    public String getTelephone() {
        return telephone;
    }
}
