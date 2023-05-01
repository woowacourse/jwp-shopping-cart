package cart.domain.settings.persistence.entity;

import cart.domain.EntityMappingException;

public class MemberEntity {

    private static final int EMAIL_LENGTH_MAX = 20;
    private static final int PASSWORD_LENGTH_MAX = 20;

    private final Long memberId;
    private final String email;
    private final String password;

    public MemberEntity(final Long memberId, final String email, final String password) {
        validateEmailLength(email);
        validatePasswordLength(password);
        this.memberId = memberId;
        this.email = email;
        this.password = password;
    }

    public MemberEntity(final String email, final String password) {
        this(null, email, password);
    }

    private void validateEmailLength(final String email) {
        if (email.length() > EMAIL_LENGTH_MAX) {
            throw new EntityMappingException("이메일의 길이는 20자 이하여야 합니다.");
        }
    }

    private void validatePasswordLength(final String password) {
        if (password.length() > PASSWORD_LENGTH_MAX) {
            throw new EntityMappingException("비밀번호의 길이는 20자 이하여야 합니다.");
        }
    }

    public Long getMemberId() {
        return memberId;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
