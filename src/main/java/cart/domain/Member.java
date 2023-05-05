package cart.domain;

public class Member {
    private static final String EMAIL_REGAX = "^[a-zA-Z0-9]*@.[a-zA-Z0-9]*$";
    private static final int EMAIL_MAX_LENGTH = 21;
    private static final double PASSWORD_MIN_LENGTH = 2;
    private static final double PASSWORD_MAX_LENGTH = 21;

    private final String email;

    private final String password;

    public Member(String email, String password) {
        validate(email, password);
        this.email = email;
        this.password = password;
    }

    private void validate(String email, String password) {
        if (email.isBlank() || email.length() > EMAIL_MAX_LENGTH) {
            throw new IllegalArgumentException("사용자 이메일은 1~20자 이내로 작성해야 합니다.");
        }
        if (!email.matches(EMAIL_REGAX)) {
            throw new IllegalArgumentException("이메일 형식을 준수해야합니다.");
        }
        if (!(PASSWORD_MIN_LENGTH < password.length() && password.length() < PASSWORD_MAX_LENGTH)) {
            throw new IllegalArgumentException("사용자 비밀번호는 3~20자 이내로 작성해야 합니다.");
        }
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
