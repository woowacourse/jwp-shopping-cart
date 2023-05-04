package cart.domain;

import static cart.exception.ErrorCode.MEMBER_EMAIL_LENGTH;
import static cart.exception.ErrorCode.MEMBER_NICKNAME_LENGTH;

import cart.exception.GlobalException;

public class Member {

    private static final int EMAIl_MIN_LENGTH = 5, EMAIL_MAX_LENGTH = 50;
    private static final int NICKNAME_MIN_LENGTH = 1, NICKNAME_MAX_LENGTH = 30;

    private final String email;
    private final MemberPassword password;
    private final String nickname;
    private final String telephone;

    private final MemberRole role;

    private Member(final String email, final MemberPassword password, final String nickname,
                   final String telephone, final MemberRole role) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.telephone = telephone;
        this.role = role;
    }

    public static Member create(final String email, final String password, final String nickname,
                                final String telephone, final MemberRole role) {
        validateEmail(email);
        validateNickname(nickname);
        return new Member(email, MemberPassword.create(password), nickname, telephone, role);
    }

    private static void validateEmail(final String email) {
        if (email.length() < EMAIl_MIN_LENGTH || email.length() > EMAIL_MAX_LENGTH) {
            throw new GlobalException(MEMBER_EMAIL_LENGTH);
        }
    }

    private static void validateNickname(final String nickname) {
        if (nickname.length() < NICKNAME_MIN_LENGTH || nickname.length() > NICKNAME_MAX_LENGTH) {
            throw new GlobalException(MEMBER_NICKNAME_LENGTH);
        }
    }

    public String getEmail() {
        return email;
    }

    public MemberRole getRole() {
        return role;
    }

    public String getPassword() {
        return password.getPassword();
    }

    public String getNickname() {
        return nickname;
    }

    public String getTelephone() {
        return telephone;
    }
}
